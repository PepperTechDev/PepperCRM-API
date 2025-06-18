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
import peppertech.crm.api.Responses.DeleteResponse;
import peppertech.crm.api.Responses.ErrorResponse;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;
import peppertech.crm.api.Tasks.Service.CardServiceI;

/**
 * Controlador REST para las operaciones relacionadas con las tarjetas.
 * <p>Este controlador proporciona métodos para gestionar tarjetas en el sistema.</p>
 *
 * @see CardServiceI
 */
@RestController
@RequestMapping("/Cards")
@CacheConfig(cacheNames = "cards")
@Tag(name = "Cards", description = "Operaciones relacionadas con tarjetas")
public class CardController {

    private final CardServiceI serviceCard;

    @Autowired
    public CardController(CardServiceI serviceCard) {
        this.serviceCard = serviceCard;
    }

    @PostMapping("/Create")
    @Operation(summary = "Crear una nueva tarjeta",
            description = "Crea una nueva tarjeta con la información proporcionada. "
                    + "Los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Tarjeta creada",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CardDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear la tarjeta, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear la tarjeta.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createCard(
            @Parameter(description = "Datos de la nueva tarjeta que se va a crear.", required = true)
            @RequestBody CardDTO cardDTO) {
        try {
            return new ResponseEntity<>(serviceCard.createCard(cardDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando tarjeta. " + e.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando tarjeta. " + e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/All")
    @Operation(summary = "Obtener todas las tarjetas",
            description = "Recupera todas las tarjetas registradas en el sistema. "
                    + "En caso de no haber tarjetas, se devolverá una excepción.",
            responses = {
                    @ApiResponse(description = "Tarjetas encontradas",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CardDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron tarjetas registradas.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllCards() {
        try {
            return new ResponseEntity<>(serviceCard.getAllCards(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error buscando tarjetas. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una tarjeta existente por su ID.
     * <p>Este método permite eliminar una tarjeta de la base de datos utilizando su ID único.
     * Si la tarjeta no se encuentra o si ocurre un error durante la eliminación, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único de la tarjeta que se desea eliminar.
     * @return Un objeto {@link ResponseEntity} con el resultado de la operación.
     */
    @DeleteMapping("/Delete/{id}")
    @Operation(summary = "Eliminar una tarjeta",
            description = "Elimina una tarjeta existente utilizando su ID. "
                    + "Si la tarjeta no existe o hay un error, se devolverá un error con código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Tarjeta eliminada",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
                    @ApiResponse(responseCode = "404",
                            description = "No se pudo eliminar la tarjeta.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> deleteCard(
            @Parameter(description = "ID único de la tarjeta que se desea eliminar.", required = true)
            @PathVariable String id) {
        try {
            String result = serviceCard.deleteCard(id);
            return new ResponseEntity<>(new DeleteResponse("Card with ID '" + id + "' was successfully deleted. " + result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to delete card with ID '" + id + "'. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
