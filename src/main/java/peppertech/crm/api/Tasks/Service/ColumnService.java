package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;

import java.util.List;

@Service
public class ColumnService implements ColumnServiceI {
    @Override
    public ColumnDTO createColumn(ColumnDTO columnDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<ColumnDTO> getAllColumns() throws Exception {
        return List.of();
    }

    @Override
    public ColumnDTO getColumnById(String id) throws Exception {
        return null;
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
        return "";
    }
}
