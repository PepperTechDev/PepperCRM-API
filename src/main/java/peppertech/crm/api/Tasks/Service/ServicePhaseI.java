package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.PhaseDTO;

import java.util.List;

public interface ServicePhaseI {
    PhaseDTO createPhase(PhaseDTO phaseDTO) throws ValidationException;

    List<PhaseDTO> getAllPhases() throws Exception;

    PhaseDTO getPhaseById(String id) throws Exception;

    List<PhaseDTO> getPhasesByName(String name) throws Exception;

    PhaseDTO updatePhase(String id, PhaseDTO updatedPhase) throws Exception;

    String deletePhase(String id) throws Exception;
}
