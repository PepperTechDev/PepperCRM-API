package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;

import java.util.List;

public interface CardServiceI {
    CardDTO createCard(CardDTO cardDTO) throws ValidationException;

    List<CardDTO> getAllCards() throws Exception;

    CardDTO getCardById(String id) throws Exception;

    List<CardDTO> getCardsByTitle(String title) throws Exception;

    List<CardDTO> getCardsByUserId(String userId) throws Exception;

    List<CardDTO> getCardsByPriority(String priority) throws Exception;

    CardDTO updateCard(String id, CardDTO updatedCard) throws Exception;

    String deleteCard(String id) throws Exception;
}
