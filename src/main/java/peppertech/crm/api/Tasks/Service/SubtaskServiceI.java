package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.SubtaskDTO;

import java.util.List;

public interface SubtaskServiceI {
    SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) throws ValidationException;

    List<SubtaskDTO> getAllSubtasks() throws Exception;

    SubtaskDTO getSubtaskById(String id) throws Exception;

    List<SubtaskDTO> getSubtasksByTitle(String title) throws Exception;

    List<SubtaskDTO> getSubtasksByUserId(String userId) throws Exception;

    List<SubtaskDTO> getSubtasksByStatus(String status) throws Exception;

    SubtaskDTO updateSubtask(String id, SubtaskDTO updatedSubtask) throws Exception;

    String deleteSubtask(String id) throws Exception;
}
