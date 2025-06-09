package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;

import java.util.List;

public interface ServiceColumnI {
    ColumnDTO createPhase(ColumnDTO columnDTO) throws ValidationException;

    List<ColumnDTO> getAllPhases() throws Exception;

    ColumnDTO getPhaseById(String id) throws Exception;

    List<ColumnDTO> getPhasesByName(String name) throws Exception;

    ColumnDTO updatePhase(String id, ColumnDTO updatedPhase) throws Exception;

    String deletePhase(String id) throws Exception;
}
