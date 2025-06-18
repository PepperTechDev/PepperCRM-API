package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.SubtaskDTO;

import java.util.List;

public class SubtaskService implements SubtaskServiceI {

    @Override
    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<SubtaskDTO> getAllSubtasks() throws Exception {
        return List.of();
    }

    @Override
    public SubtaskDTO getSubtaskById(String id) throws Exception {
        return null;
    }

    @Override
    public List<SubtaskDTO> getSubtasksByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public List<SubtaskDTO> getSubtasksByUserId(String userId) throws Exception {
        return List.of();
    }

    @Override
    public List<SubtaskDTO> getSubtasksByStatus(String status) throws Exception {
        return List.of();
    }

    @Override
    public SubtaskDTO updateSubtask(String id, SubtaskDTO updatedSubtask) throws Exception {
        return null;
    }

    @Override
    public String deleteSubtask(String id) throws Exception {
        return "";
    }
}
