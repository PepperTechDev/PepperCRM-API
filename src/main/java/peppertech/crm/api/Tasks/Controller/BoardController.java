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
import peppertech.crm.api.Tasks.Service.ServiceBoardI;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Service.SUserI;

/**
 * Controlador REST para las operaciones relacionadas con los tableros.
 * <p>Este controlador proporciona una serie de métodos para gestionar tableros en el sistema.</p>
 *
 * @see SUserI
 */
@RestController
@RequestMapping("/Boards")
@CacheConfig(cacheNames = "boards")
@Tag(name = "Boards", description = "Operations related to boards")
public class BoardController {

    private final ServiceBoardI serviceBoard;

    @Autowired
    public BoardController(ServiceBoardI serviceBoard) {
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
}
