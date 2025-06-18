package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peppertech.crm.api.Tasks.Mapper.BoardMapper;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;
import peppertech.crm.api.Tasks.Repository.BoardRepository;
import peppertech.crm.api.Tasks.Validator.BoardValidatorI;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@Service
public class BoardService implements BoardServiceI {
    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;
    private final BoardValidatorI validatorBoard;

    @Autowired
    public BoardService(BoardRepository boardRepository, BoardMapper boardMapper, BoardValidatorI validatorBoard) {
        this.boardRepository = boardRepository;
        this.boardMapper = boardMapper;
        this.validatorBoard = validatorBoard;
    }

    @Override
    @Transactional
    @CachePut(value = "boards", key = "#result.id")
    public BoardDTO createBoard(BoardDTO boardDTO) throws ValidationException, IllegalStateException {
        return Optional.of(boardDTO)
                .map(dto -> {
                    validatorBoard.validateName(dto.getName());
                    validatorBoard.validateStatus(dto.getStatus());
                    validatorBoard.validateId(dto.getOwner());
                    validatorBoard.validateListId(dto.getAdministrators());
                    validatorBoard.validateListId(dto.getEditors());
                    validatorBoard.validateListId(dto.getViewers());
                    validatorBoard.validateFutureDate(dto.getEndDate());
                    validatorBoard.validateDate(dto.getStartDate());
                    if (!validatorBoard.isValid()) {
                        throw new ValidationException(validatorBoard.getErrors().toString());
                    }
                    validatorBoard.Reset();
                    return dto;
                })
                .map(dto -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    dto.setCreatedAt(LocalDateTime.now().format(formatter));
                    return dto;
                })
                .map(boardMapper::toEntity)
                .map(boardRepository::save)
                .map(boardMapper::toDTO)
                .orElseThrow(() -> new IllegalStateException("The Board could not be created\n"));
    }

    @Override
    @Cacheable(value = "boards", key = "'all_boards'")
    public List<BoardDTO> getAllBoards() throws Exception {
        return Optional.of(boardRepository.findAll())
                .filter(boards -> !boards.isEmpty())
                .map(users -> users.stream()
                        .map(boardMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("There is no board."));
    }

    /**
     * Obtiene un tablero de la base de datos a partir de su ID.
     * <br><br>
     * Este método busca un tablero en la base de datos utilizando el ID proporcionado.
     * Si el tablero existe, se valida el ID utilizando el validador. Si el ID es válido,
     * se convierte el tablero de entidad a DTO y se devuelve. Si el tablero no existe o
     * el ID no es válido, se lanza una excepción correspondiente.
     *
     * @param id el ID del tablero que se desea obtener. El ID debe ser una cadena que representa un {@link ObjectId}.
     * @return un objeto {@link BoardDTO} que representa al tablero encontrado.
     * @throws Exception           si el tablero no existe en la base de datos o si el ID no es válido.
     * @throws ValidationException si el ID proporcionado no es válido según las reglas de validación.
     * @see BoardDTO
     * @see ValidationException
     */
    @Override
    @Cacheable(value = "boards", key = "'id_'+#id")
    public BoardDTO getBoardById(String id) throws Exception {
        return Optional.of(id)
                .map(validId -> {
                    validatorBoard.validateId(validId);
                    if (!validatorBoard.isValid()) {
                        throw new ValidationException(validatorBoard.getErrors().toString());
                    }
                    validatorBoard.Reset();
                    return new ObjectId(validId);
                })
                .flatMap(boardRepository::findById)
                .map(boardMapper::toDTO)
                .orElseThrow(() -> new Exception("Board does not exist."));
    }

    @Override
    public List<BoardDTO> getBoardsByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public BoardDTO updateBoard(String id, BoardDTO updatedBoard) throws Exception {
        return null;
    }

    /**
     * Elimina un tablero de la base de datos.
     * <br><br>
     * Este método permite eliminar un tablero utilizando su ID. Primero, verifica si el tablero existe.
     * Si el tablero existe, se elimina de la base de datos. Si no se encuentra el tablero con el ID proporcionado,
     * se lanza una excepción indicando que el tablero no existe.
     * <br><br>
     * <b>Transacciones (Spring Boot):</b> Este método está anotado con `@Transactional`, lo que garantiza que si ocurre
     * algún error durante la eliminación (por ejemplo, si el tablero no existe o hay un fallo en la base de datos),
     * la transacción será revertida y no se eliminará el tablero.
     * <br><br>
     * <b>Rollback:</b> Si ocurre una excepción de tipo `RuntimeException`, Spring realizará un rollback automáticamente
     * para mantener la integridad de los datos.
     *
     * @param id el identificador del tablero que se desea eliminar.
     * @return un mensaje indicando que el tablero ha sido eliminado correctamente.
     * @throws Exception si el tablero no existe o si ocurre un error durante el proceso de eliminación.
     * @see BoardDTO
     * @see Transactional
     */
    @Override
    @Transactional
    @CacheEvict(value = "boards", key = "'id_'+#id")
    public String deleteBoard(String id) throws Exception {
        return Optional.of(getBoardById(id))
                .map(board -> {
                    boardRepository.deleteById(new ObjectId(board.getId()));
                    return "The board with ID '" + id + "' has been deleted.";
                })
                .orElseThrow(() -> new Exception("Board not found."));
    }
}
