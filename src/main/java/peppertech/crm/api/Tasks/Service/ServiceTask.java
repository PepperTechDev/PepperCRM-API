package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Mapper.MapperTask;
import peppertech.crm.api.Tasks.Model.DTO.TaskDTO;
import peppertech.crm.api.Tasks.Model.Entity.Task;
import peppertech.crm.api.Tasks.Repository.RepositoryTask;
import peppertech.crm.api.Tasks.Mapper.MapperTask;
import peppertech.crm.api.Tasks.Repository.RepositoryTask;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceTask implements ServiceTaskI {

    private final RepositoryTask repositoryTask;
    private final MapperTask mapperTask;

    @Autowired
    public ServiceTask(RepositoryTask repositoryTask, MapperTask mapperTask) {
        this.repositoryTask = repositoryTask;
        this.mapperTask = mapperTask;
    }


    @Override
    public TaskDTO createTask(TaskDTO taskDTO) throws ValidationException {
        return Optional.ofNullable(taskDTO)
                .filter(dto -> dto.getId() == null) // No permitir crear con ID
                .map(dto -> {
                    dto.setCreatedAt(new Date());
                    return dto;
                })
                .map(mapperTask::toEntity)
                .map(repositoryTask::save)
                .map(mapperTask::toDTO)
                .orElseThrow(() -> new IllegalStateException("The task already has an ID assigned or the DTO is null"));
    }

    @Override
    public List<TaskDTO> getAllTasks() throws Exception {
        List<Task> tasks = repositoryTask.findAll();
        return tasks.stream().map(mapperTask::toDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO getTaskById(String id) throws Exception {
        Task task = repositoryTask.getTaskById(id)
                .orElseThrow(() -> new Exception("Task not found with id: " + id));
        return mapperTask.toDTO(task);
    }

    @Override
    public List<TaskDTO> getTasksByTitle(String title) throws Exception {
        List<Task> tasks = repositoryTask.findByTitleContainingIgnoreCase(title);
        return tasks.stream().map(mapperTask::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByUserId(String userId) throws Exception {
        List<TaskDTO> tasks = repositoryTask.getTaskById(userId);
        return tasks.stream().map(mapperTask::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> getTasksByPriority(String priority) throws Exception {
        List<Task> tasks = repositoryTask.findByPriorityIgnoreCase(priority);
        return tasks.stream().map(mapperTask::toDTO).collect(Collectors.toList());
    }

    @Override
    public TaskDTO updateTask(String id, TaskDTO updatedTask) throws Exception {
        Task existing = repositoryTask.findById(id)
                .orElseThrow(() -> new Exception("Task not found with id: " + id));

        existing.setTitle(updatedTask.getTitle());
        existing.setDescription(updatedTask.getDescription());
        existing.setDueDate(updatedTask.getDueDate());
        existing.setPriority(updatedTask.getPriority());
        existing.setId(updatedTask.getId());

        Task saved = repositoryTask.save(existing);
        return mapperTask.toDTO(saved);
    }

    @Override
    public String deleteTask(String id) throws Exception {
        Task task = repositoryTask.findById(id)
                .orElseThrow(() -> new Exception("Task not found with id: " + id));
        repositoryTask.delete(task);
        return "Task deleted successfully.";
    }
}
