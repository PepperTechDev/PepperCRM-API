package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.PhaseDTO;

import java.util.List;

public class ServicePhase implements ServicePhaseI {
    @Override
    public PhaseDTO createPhase(PhaseDTO phaseDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<PhaseDTO> getAllPhases() throws Exception {
        return List.of();
    }

    @Override
    public PhaseDTO getPhaseById(String id) throws Exception {
        return null;
    }

    @Override
    public List<PhaseDTO> getPhasesByName(String name) throws Exception {
        return List.of();
    }

    @Override
    public PhaseDTO updatePhase(String id, PhaseDTO updatedPhase) throws Exception {
        return null;
    }

    @Override
    public String deletePhase(String id) throws Exception {
        return "";
    }
}
