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
import peppertech.crm.api.Tasks.Model.DTO.SubtaskDTO;
import peppertech.crm.api.Tasks.Service.SubtaskServiceI;

/**
 * Controlador REST para las operaciones relacionadas con las subtareas.
 * <p>Este controlador proporciona métodos para gestionar subtareas en el sistema.</p>
 *
 * @see SubtaskServiceI
 */
@RestController
@RequestMapping("/Subtasks")
@CacheConfig(cacheNames = "subtasks")
@Tag(name = "Subtasks", description = "Operaciones relacionadas con subtareas")
public class SubtaskController {

    private final SubtaskServiceI serviceSubtask;

    @Autowired
    public SubtaskController(SubtaskServiceI serviceSubtask) {
        this.serviceSubtask = serviceSubtask;
    }

    @PostMapping("/Create")
    @Operation(summary = "Crear una nueva subtarea",
            description = "Crea una nueva subtarea con la información proporcionada. Los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Subtarea creada",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = SubtaskDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear la subtarea, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear la subtarea.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createSubtask(
            @Parameter(description = "Datos de la nueva subtarea que se va a crear.", required = true)
            @RequestBody SubtaskDTO subtaskDTO) {
        try {
            return new ResponseEntity<>(serviceSubtask.createSubtask(subtaskDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando subtarea. " + e.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando subtarea. " + e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/All")
    @Operation(summary = "Obtener todas las subtareas",
            description = "Recupera todas las subtareas registradas en el sistema. En caso de no haber subtareas, se devolverá una excepción.",
            responses = {
                    @ApiResponse(description = "Subtareas encontradas",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = SubtaskDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron subtareas registradas.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllSubtasks() {
        try {
            return new ResponseEntity<>(serviceSubtask.getAllSubtasks(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error buscando subtareas. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una subtarea existente por su ID.
     * <p>Este método permite eliminar una subtarea de la base de datos utilizando su ID único.
     * Si la subtarea no se encuentra o si ocurre un error durante la eliminación, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único de la subtarea que se desea eliminar.
     * @return Un objeto {@link ResponseEntity} con el resultado de la operación.
     */
    @DeleteMapping("/Delete/{id}")
    @Operation(summary = "Eliminar una subtarea",
            description = "Elimina una subtarea existente utilizando su ID. Si la subtarea no existe o hay un error, se devolverá un error con código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Subtarea eliminada",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
                    @ApiResponse(responseCode = "404",
                            description = "No se pudo eliminar la subtarea.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> deleteSubtask(
            @Parameter(description = "ID único de la subtarea que se desea eliminar.", required = true)
            @PathVariable String id) {
        try {
            String result = serviceSubtask.deleteSubtask(id);
            return new ResponseEntity<>(new DeleteResponse("Subtask with ID '" + id + "' was successfully deleted. " + result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to delete subtask with ID '" + id + "'. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}