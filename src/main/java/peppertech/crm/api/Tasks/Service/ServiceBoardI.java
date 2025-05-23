package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;

import java.util.List;

public interface ServiceBoardI {
    BoardDTO createBoard(BoardDTO boardDTO) throws ValidationException;

    List<BoardDTO> getAllBoards() throws Exception;

    BoardDTO getBoardById(String id) throws Exception;

    List<BoardDTO> getBoardsByTitle(String title) throws Exception;

    BoardDTO updateBoard(String id, BoardDTO updatedBoard) throws Exception;

    String deleteBoard(String id) throws Exception;
}
