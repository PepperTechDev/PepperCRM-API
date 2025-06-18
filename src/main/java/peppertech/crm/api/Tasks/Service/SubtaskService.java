package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Mapper.SubtaskMapper;
import peppertech.crm.api.Tasks.Model.DTO.SubtaskDTO;
import peppertech.crm.api.Tasks.Repository.SubtaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubtaskService implements SubtaskServiceI {
    private final SubtaskRepository subtaskRepository;
    private final SubtaskMapper subtaskMapper;

    @Autowired
    public SubtaskService(SubtaskRepository subtaskRepository, SubtaskMapper subtaskMapper) {
        this.subtaskRepository = subtaskRepository;
        this.subtaskMapper = subtaskMapper;
    }


    @Override
    public SubtaskDTO createSubtask(SubtaskDTO subtaskDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<SubtaskDTO> getAllSubtasks() throws Exception {
        return Optional.of(subtaskRepository.findAll())
                .filter(boards -> !boards.isEmpty())
                .map(users -> users.stream()
                        .map(subtaskMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("There is no subtask."));
    }

    @Override
    public SubtaskDTO getSubtaskById(String id) throws Exception {
        return Optional.of(id)
                .map(ObjectId::new)
                .flatMap(subtaskRepository::findById)
                .map(subtaskMapper::toDTO)
                .orElseThrow(() -> new Exception("Substask does not exist."));
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
        return Optional.of(getSubtaskById(id))
                .map(board -> {
                    subtaskRepository.deleteById(new ObjectId(board.getId()));
                    return "The Subtask with ID '" + id + "' has been deleted.";
                })
                .orElseThrow(() -> new Exception("Subtask not found."));
    }
}
