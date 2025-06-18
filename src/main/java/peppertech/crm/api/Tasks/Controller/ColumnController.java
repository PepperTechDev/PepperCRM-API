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
import peppertech.crm.api.Tasks.Model.DTO.ColumnDTO;
import peppertech.crm.api.Tasks.Service.ColumnServiceI;

/**
 * Controlador REST para las operaciones relacionadas con las columnas.
 * <p>Este controlador proporciona métodos para gestionar columnas en el sistema.</p>
 *
 * @see ColumnServiceI
 */
@RestController
@RequestMapping("/Columns")
@CacheConfig(cacheNames = "columns")
@Tag(name = "Columns", description = "Operaciones relacionadas con columnas")
public class ColumnController {

    private final ColumnServiceI serviceColumn;

    @Autowired
    public ColumnController(ColumnServiceI serviceColumn) {
        this.serviceColumn = serviceColumn;
    }

    @PostMapping("/Create")
    @Operation(summary = "Crear una nueva columna",
            description = "Crea una nueva columna con la información proporcionada. Los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Columna creada",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ColumnDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear la columna, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear la columna.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createColumn(
            @Parameter(description = "Datos de la nueva columna que se va a crear.", required = true)
            @RequestBody ColumnDTO columnDTO) {
        try {
            return new ResponseEntity<>(serviceColumn.createColumn(columnDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando columna. " + e.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error creando columna. " + e.getMessage(), HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/All")
    @Operation(summary = "Obtener todas las columnas",
            description = "Recupera todas las columnas registradas en el sistema. En caso de no haber columnas, se devolverá una excepción.",
            responses = {
                    @ApiResponse(description = "Columnas encontradas",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ColumnDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron columnas registradas.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllColumns() {
        try {
            return new ResponseEntity<>(serviceColumn.getAllColumns(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error buscando columnas. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una columna existente por su ID.
     * <p>Este método permite eliminar una columna de la base de datos utilizando su ID único.
     * Si la columna no se encuentra o si ocurre un error durante la eliminación, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único de la columna que se desea eliminar.
     * @return Un objeto {@link ResponseEntity} con el resultado de la operación.
     */
    @DeleteMapping("/Delete/{id}")
    @Operation(summary = "Eliminar una columna",
            description = "Elimina una columna existente utilizando su ID. Si la columna no existe o hay un error, se devolverá un error con código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Columna eliminada",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeleteResponse.class))),
                    @ApiResponse(responseCode = "404",
                            description = "No se pudo eliminar la columna.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> deleteColumn(
            @Parameter(description = "ID único de la columna que se desea eliminar.", required = true)
            @PathVariable String id) {
        try {
            String result = serviceColumn.deleteColumn(id);
            return new ResponseEntity<>(new DeleteResponse("Column with ID '" + id + "' was successfully deleted. " + result), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Failed to delete column with ID '" + id + "'. " + e.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
