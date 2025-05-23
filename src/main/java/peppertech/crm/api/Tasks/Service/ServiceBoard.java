package peppertech.crm.api.Tasks.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;

import java.util.List;

public class ServiceBoard implements ServiceBoardI{
    @Override
    public BoardDTO createBoard(BoardDTO boardDTO) throws ValidationException {
        return null;
    }

    @Override
    public List<BoardDTO> getAllBoards() throws Exception {
        return List.of();
    }

    @Override
    public BoardDTO getBoardById(String id) throws Exception {
        return null;
    }

    @Override
    public List<BoardDTO> getBoardsByTitle(String title) throws Exception {
        return List.of();
    }

    @Override
    public BoardDTO updateBoard(String id, BoardDTO updatedBoard) throws Exception {
        return null;
    }

    @Override
    public String deleteBoard(String id) throws Exception {
        return "";
    }
}
