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
    public CardDTO createCard(CardDTO cardDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<CardDTO> getAllCards() throws Exception {
        return List.of();
    }

    @Override
    public CardDTO getCardById(String id) throws Exception {
        return null;
    }

    @Override
    public List<CardDTO> getCardsByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public List<CardDTO> getCardsByUserId(String userId) throws Exception {
        return List.of();
    }

    @Override
    public List<CardDTO> getCardsByPriority(String priority) throws Exception {
        return List.of();
    }

    @Override
    public CardDTO updateCard(String id, CardDTO updatedCard) throws Exception {
        return null;
    }

    @Override
    public String deleteCard(String id) throws Exception {
        return "";
    }
}
