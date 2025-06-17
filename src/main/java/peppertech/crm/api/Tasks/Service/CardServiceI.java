package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;

import java.util.List;

public interface CardServiceI {
    CardDTO createTask(CardDTO cardDTO) throws ValidationException;

    List<CardDTO> getAllTasks() throws Exception;

    CardDTO getTaskById(String id) throws Exception;

    List<CardDTO> getTasksByTitle(String title) throws Exception;

    List<CardDTO> getTasksByUserId(String userId) throws Exception;

    List<CardDTO> getTasksByPriority(String priority) throws Exception;

    CardDTO updateTask(String id, CardDTO updatedTask) throws Exception;

    String deleteTask(String id) throws Exception;
}
