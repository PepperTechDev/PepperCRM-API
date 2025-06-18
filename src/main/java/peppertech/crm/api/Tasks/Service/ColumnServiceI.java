package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;

import java.util.List;

public interface ColumnServiceI {
    ColumnDTO createColumn(ColumnDTO columnDTO) throws ValidationException;

    List<ColumnDTO> getAllColumns() throws Exception;

    ColumnDTO getColumnById(String id) throws Exception;

    List<ColumnDTO> getColumnByName(String name) throws Exception;

    ColumnDTO updateColumn(String id, ColumnDTO updatedPhase) throws Exception;

    String deleteColumn(String id) throws Exception;
}
