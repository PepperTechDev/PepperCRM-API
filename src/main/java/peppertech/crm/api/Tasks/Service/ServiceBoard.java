package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peppertech.crm.api.Tasks.Mapper.MapperBoard;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;
import peppertech.crm.api.Tasks.Repository.RepositoryBoard;
import peppertech.crm.api.Tasks.Validator.ValidatorBoardI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Data
@Builder
@Service
public class ServiceBoard implements ServiceBoardI{
    private final RepositoryBoard repositoryBoard;
    private final MapperBoard mapperBoard;
    private final ValidatorBoardI validatorBoard;

    @Autowired
    public ServiceBoard(RepositoryBoard repositoryBoard, MapperBoard mapperBoard, ValidatorBoardI validatorBoard) {
        this.repositoryBoard = repositoryBoard;
        this.mapperBoard = mapperBoard;
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
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    dto.setCreatedAt(formatter.format(new Date()));
                    return dto;
                })
                .map(mapperBoard::toEntity)
                .map(repositoryBoard::save)
                .map(mapperBoard::toDTO)
                .orElseThrow(() -> new IllegalStateException("The Board could not be created\n"));
    }

    @Override
    @Cacheable(value = "boards", key = "'all_boards'")
    public List<BoardDTO> getAllBoards() throws Exception {
        return Optional.of(repositoryBoard.findAll())
                .filter(boards -> !boards.isEmpty())
                .map(users -> users.stream()
                        .map(mapperBoard::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("There is no board."));
    }

    @Override
    public BoardDTO getBoardById(String id) throws Exception {
        return null;
    }

    @Override
    public List<BoardDTO> getBoardsByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public BoardDTO updateBoard(String id, BoardDTO updatedBoard) throws Exception {
        return null;
    }

    @Override
    public String deleteBoard(String id) throws Exception {
        return "";
    }
}
