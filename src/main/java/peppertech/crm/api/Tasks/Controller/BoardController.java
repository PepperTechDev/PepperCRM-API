package peppertech.crm.api.Tasks.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peppertech.crm.api.Responses.ErrorResponse;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;
import peppertech.crm.api.Tasks.Service.BoardServiceI;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Service.UserServiceI;

/**
 * Controlador REST para las operaciones relacionadas con los tableros.
 * <p>Este controlador proporciona una serie de métodos para gestionar tableros en el sistema.</p>
 *
 * @see UserServiceI
 */
@RestController
@RequestMapping("/Boards")
@CacheConfig(cacheNames = "boards")
@Tag(name = "Boards", description = "Operations related to boards")
public class BoardController {

    private final BoardServiceI serviceBoard;

    @Autowired
    public BoardController(BoardServiceI serviceBoard) {
        this.serviceBoard = serviceBoard;
    }

    @PostMapping("/Create")
    @Operation(summary = "Crear un nuevo tablero",
            description = "Crea un nuevo tablero con la información proporcionada. "
                    + "El correo electrónico debe ser único y los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Tablero Creado",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear el tablero, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear el tablero.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createUser(
            @Parameter(description = "Datos del nuevo tablero que se va a crear. Debe incluir nombre, descripcion y color.",
                    required = true) @RequestBody BoardDTO boardDTO) {
        try {
            return new ResponseEntity<>(serviceBoard.createBoard(boardDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creating board. " + e.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creating board. " + e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/All")
    @Operation(summary = "Obtener todos los tableros",
            description = "Recupera todos los tableros registrados en el sistema. "
                    + "En caso de no haber tableros, se devolverá una excepción.",
            responses = {
                    @ApiResponse(description = "Tableros encontrados",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron tableros registrados.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(serviceBoard.getAllBoards(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error searching for boards. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina un tablero existente por su ID.
     * <p>Este método permite eliminar un tablero de la base de datos utilizando su ID único.
     * Si el tablero no se encuentra o si ocurre un error durante la eliminación, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único del tablero que se desea eliminar. Este parámetro debe ser el ID del tablero a eliminar.
     * @return Un objeto {@link ResponseEntity} con:
     *         <ul>
     *           <li>Un mensaje indicando el éxito de la eliminación con el código HTTP 200.</li>
     *           <li>Un mensaje de error si no se pudo eliminar el tablero, con el código HTTP 404.</li>
     *         </ul>
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: El tablero fue eliminado exitosamente.<br></li>
     *   <li><b>404 Not Found</b>: No se encontró el tablero con el ID proporcionado o hubo un error al eliminarlo.<br></li>
     * </ul>
     */
    @DeleteMapping("/Delete/{id}")
    @Operation(summary = "Eliminar un tablero",
            description = "Elimina un tablero existente utilizando su ID. "
                    + "Si el tablero no existe o hay un error, se devolverá un error con código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Tablero eliminado",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = peppertech.crm.api.Responses.DeleteResponse.class))),
                    @ApiResponse(responseCode = "404",
                            description = "No se pudo eliminar el tablero.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> deleteBoard(@Parameter(description = "ID único del tablero que se desea eliminar.", required = true) @PathVariable String id) {
        try {
            String result = serviceBoard.deleteBoard(id);
            return new ResponseEntity<>(new peppertech.crm.api.Responses.DeleteResponse("Board with ID '" + id + "' was successfully deleted. " + result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to delete board with ID '" + id + "'. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
