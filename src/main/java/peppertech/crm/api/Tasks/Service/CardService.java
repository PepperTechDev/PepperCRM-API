package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Mapper.CardMapper;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;
import peppertech.crm.api.Tasks.Repository.CardRepository;

import java.util.List;

@Service
public class CardService implements CardServiceI {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;

    @Autowired
    public CardService(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }

    @Override
    public CardDTO createTask(CardDTO cardDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<CardDTO> getAllTasks() throws Exception {
        return List.of();
    }

    @Override
    public CardDTO getTaskById(String id) throws Exception {
        return null;
    }

    @Override
    public List<CardDTO> getTasksByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public List<CardDTO> getTasksByUserId(String userId) throws Exception {
        return List.of();
    }

    @Override
    public List<CardDTO> getTasksByPriority(String priority) throws Exception {
        return List.of();
    }

    @Override
    public CardDTO updateTask(String id, CardDTO updatedTask) throws Exception {
        return null;
    }

    @Override
    public String deleteTask(String id) throws Exception {
        return "";
    }
}
