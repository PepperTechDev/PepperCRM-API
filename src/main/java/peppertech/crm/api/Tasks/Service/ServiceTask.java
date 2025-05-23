package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.TaskDTO;

import java.util.List;

public class ServiceTask implements ServiceTaskI {
    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<TaskDTO> getAllTasks() throws Exception {
        return List.of();
    }

    @Override
    public TaskDTO getTaskById(String id) throws Exception {
        return null;
    }

    @Override
    public List<TaskDTO> getTasksByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public List<TaskDTO> getTasksByUserId(String userId) throws Exception {
        return List.of();
    }

    @Override
    public List<TaskDTO> getTasksByPriority(String priority) throws Exception {
        return List.of();
    }

    @Override
    public TaskDTO updateTask(String id, TaskDTO updatedTask) throws Exception {
        return null;
    }

    @Override
    public String deleteTask(String id) throws Exception {
        return "";
    }
}
