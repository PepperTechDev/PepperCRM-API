package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Mapper.ColumnMapper;
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;
import peppertech.crm.api.Tasks.Repository.ColumnRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColumnService implements ColumnServiceI {
    private final ColumnRepository columnRepository;
    private final ColumnMapper columnMapper;

    public ColumnService(ColumnRepository columnRepository, ColumnMapper columnMapper) {
        this.columnMapper = columnMapper;
        this.columnRepository = columnRepository;
    }

    @Override
    public ColumnDTO createColumn(ColumnDTO columnDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<ColumnDTO> getAllColumns() throws Exception {
        return Optional.of(columnRepository.findAll())
                .filter(boards -> !boards.isEmpty())
                .map(users -> users.stream()
                        .map(columnMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("There is no column."));
    }

    @Override
    public ColumnDTO getColumnById(String id) throws Exception {
        return Optional.of(id)
                .map(ObjectId::new)
                .flatMap(columnRepository::findById)
                .map(columnMapper::toDTO)
                .orElseThrow(() -> new Exception("Column does not exist."));
    }

    @Override
    public List<ColumnDTO> getColumnByName(String name) throws Exception {
        return List.of();
    }

    @Override
    public ColumnDTO updateColumn(String id, ColumnDTO updatedPhase) throws Exception {
        return null;
    }

    @Override
    public String deleteColumn(String id) throws Exception {
        return Optional.of(getColumnById(id))
                .map(board -> {
                    columnRepository.deleteById(new ObjectId(board.getId()));
                    return "The Column with ID '" + id + "' has been deleted.";
                })
                .orElseThrow(() -> new Exception("Column not found."));
    }
}
