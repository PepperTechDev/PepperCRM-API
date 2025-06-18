package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Tasks.Mapper.CardMapper;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;
import peppertech.crm.api.Tasks.Repository.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return Optional.of(cardRepository.findAll())
                .filter(boards -> !boards.isEmpty())
                .map(users -> users.stream()
                        .map(cardMapper::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("There is no card."));
    }

    @Override
    public CardDTO getCardById(String id) throws Exception {
        return Optional.of(id)
                .map(ObjectId::new)
                .flatMap(cardRepository::findById)
                .map(cardMapper::toDTO)
                .orElseThrow(() -> new Exception("Card does not exist."));
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
        return Optional.of(getCardById(id))
                .map(board -> {
                    cardRepository.deleteById(new ObjectId(board.getId()));
                    return "The Card with ID '" + id + "' has been deleted.";
                })
                .orElseThrow(() -> new Exception("Card not found."));
    }
}
