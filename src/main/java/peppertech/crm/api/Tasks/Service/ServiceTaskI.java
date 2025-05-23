package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.TaskDTO;

import java.util.List;

public interface ServiceTaskI {
    TaskDTO createTask(TaskDTO taskDTO) throws ValidationException;

    List<TaskDTO> getAllTasks() throws Exception;

    TaskDTO getTaskById(String id) throws Exception;

    List<TaskDTO> getTasksByTitle(String title) throws Exception;

    List<TaskDTO> getTasksByUserId(String userId) throws Exception;

    List<TaskDTO> getTasksByPriority(String priority) throws Exception;

    TaskDTO updateTask(String id, TaskDTO updatedTask) throws Exception;

    String deleteTask(String id) throws Exception;
}
