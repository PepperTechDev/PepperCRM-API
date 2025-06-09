package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;

import java.util.List;

public class ServiceColumn implements ServiceColumnI {
    @Override
    public ColumnDTO createPhase(ColumnDTO columnDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<ColumnDTO> getAllPhases() throws Exception {
        return List.of();
    }

    @Override
    public ColumnDTO getPhaseById(String id) throws Exception {
        return null;
    }

    @Override
    public List<ColumnDTO> getPhasesByName(String name) throws Exception {
        return List.of();
    }

    @Override
    public ColumnDTO updatePhase(String id, ColumnDTO updatedPhase) throws Exception {
        return null;
    }

    @Override
    public String deletePhase(String id) throws Exception {
        return "";
    }
}
