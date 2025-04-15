package peppertech.crm.api.Leads.Controller;

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
import peppertech.crm.api.Leads.Model.DTO.LeadDTO;
import peppertech.crm.api.Leads.Service.SLeadI;
import peppertech.crm.api.Responses.ErrorResponse;

/**
 * Controlador REST para las operaciones relacionadas con los leads.
 * <p>Este controlador proporciona una serie de métodos para gestionar los leads en el sistema.</p>
 *
 * @see SLeadI
 */
@RestController
@RequestMapping("/Leads")
@CacheConfig(cacheNames = "leads")
@Tag(name = "Leads", description = "Operaciones relacionadas con leads")
public class LeadController {
    private final SLeadI serviceLead;

    /**
     * Constructor del controlador {@link LeadController}.
     * <p>Se utiliza la inyección de dependencias para asignar el servicio {@link SLeadI} que gestionará las operaciones
     * relacionadas con los leads.</p>
     *
     * @param serviceLead El servicio que contiene la lógica de negocio para manejar los leads.
     * @throws NullPointerException Si el servicio proporcionado es {@code null}.
     * @see SLeadI
     */
    @Autowired
    public LeadController(SLeadI serviceLead) {
        this.serviceLead = serviceLead;
    }

    /**
     * Crea un nuevo lead en el sistema con la información proporcionada.
     * <p>Recupera los datos del lead desde el objeto {@link LeadDTO} proporcionado en el cuerpo de la solicitud.</p>
     * <p>El correo electrónico debe ser único, y el sistema validará que los campos obligatorios como nombre y apellido estén completos.</p>
     * <p>Si se crea con éxito, se devolverá el lead generado con el código HTTP 201.</p>
     * <p>En caso de error (por ejemplo, si el correo electrónico ya está registrado), se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param leadDTO El objeto {@link LeadDTO} que contiene la información del nuevo lead.
     * @return Un objeto {@link ResponseEntity} con el lead creado (código HTTP 201) o un mensaje de error (código HTTP 404).
     */
    @PostMapping("/Create")
    @Operation(summary = "Crear un nuevo lead",
            description = "Crea un nuevo lead con la información proporcionada. "
                    + "El correo electrónico debe ser único y los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Lead Creado",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = LeadDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear el lead, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear el lead.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createLead(
            @Parameter(description = "Datos del nuevo lead que se va a crear. Debe incluir nombre, apellido y un correo electrónico único.",
                    required = true) @RequestBody LeadDTO leadDTO) {
        try {
            return new ResponseEntity<>(serviceLead.createLead(leadDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error al crear el lead [" + e.getMessage() + "]", HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error al crear el lead [" + e.getMessage() + "]", HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    /**
     * Recupera todos los leads registrados en el sistema.
     * <p>Este método devuelve una lista con todos los leads registrados. Si no se encuentran leads,
     * se devolverá una lista vacía con el código HTTP 200.</p>
     * <p>Si ocurre algún error al intentar recuperar los leads, como problemas con la base de datos o un fallo en el servicio,
     * se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @return Un objeto {@link ResponseEntity} que contiene una lista de objetos {@link LeadDTO} con los leads registrados.
     * En caso de error, se devuelve un mensaje de error con el código HTTP 404.
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Se retorna una lista de objetos {@link LeadDTO} con la información de todos los leads registrados.</li>
     *   <li><b>404 Not Found</b>: No se encontraron leads registrados o hubo un error al recuperar los datos.</li>
     * </ul>
     */
    @GetMapping("/All")
    @Operation(summary = "Obtener todos los leads",
            description = "Recupera todos los leads registrados en el sistema. "
                    + "Si no se encuentran leads, se retornará una lista vacía.",
            responses = {
                    @ApiResponse(description = "Leads encontrados",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = LeadDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron leads registrados o hubo un error al recuperar los datos.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllLeads() {
        try {
            return new ResponseEntity<>(serviceLead.getAllLeads(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al buscar los leads: " + e.getMessage(), HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    /**
     * Busca un lead utilizando su ID único.
     * <p>Este método recupera un lead de la base de datos utilizando su identificador único. Si el lead con el ID proporcionado existe, se devolverá un objeto {@link LeadDTO} con la información del lead.</p>
     * <p>Si no se encuentra un lead con el ID proporcionado, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único del lead que se desea recuperar. Este parámetro debe ser el ID único del lead.
     *           El ID es obligatorio para la búsqueda del lead.
     * @return Un objeto {@link ResponseEntity} que contiene:
     *         <ul>
     *           <li>El lead correspondiente al ID si se encuentra (código HTTP 200).</li>
     *           <li>Un mensaje de error si no se encuentra el lead (código HTTP 404).</li>
     *         </ul>
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Si el lead es encontrado, se retorna un objeto {@link LeadDTO} con los detalles del lead.<br></li>
     *   <li><b>404 Not Found</b>: Si no se encuentra el lead con el ID proporcionado, se retorna un objeto {@link ErrorResponse} con un mensaje de error.<br></li>
     * </ul>
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar lead por ID",
            description = "Recupera un lead utilizando su ID único. "
                    + "Si el lead no existe, se devolverá un error con el código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Lead encontrado",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Lead no encontrado con el ID especificado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getLeadById(@PathVariable @Parameter(description = "ID único del lead a buscar.", required = true) String id) {
        try {
            return new ResponseEntity<>(serviceLead.getLeadById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al buscar el lead con id '" + id + "' [" + e.getMessage() + "]", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }


    /**
     * Busca un lead por su dirección de correo electrónico único.
     * <p>Este método recupera un lead del sistema utilizando su correo electrónico único. Si el lead con el correo electrónico proporcionado existe, se devolverá un objeto {@link LeadDTO} con la información del lead.</p>
     * <p>Si no se encuentra un lead con el correo electrónico proporcionado, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param email La dirección de correo electrónico del lead. Este parámetro debe ser único en el sistema y se utilizará para buscar al lead.
     * @return Un objeto {@link ResponseEntity} que contiene:
     *         <ul>
     *           <li>El lead correspondiente al correo electrónico proporcionado si se encuentra (código HTTP 200).</li>
     *           <li>Un mensaje de error si no se encuentra el lead (código HTTP 404).</li>
     *         </ul>
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Si el lead es encontrado, se retorna un objeto {@link LeadDTO} con los detalles del lead.<br></li>
     *   <li><b>404 Not Found</b>: Si no se encuentra el lead con el correo electrónico proporcionado, se retorna un objeto {@link ErrorResponse} con un mensaje de error.<br></li>
     * </ul>
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar lead por correo electrónico",
            description = "Recupera un lead utilizando su correo electrónico único. "
                    + "Si no se encuentra el lead, se devolverá un error.",
            responses = {
                    @ApiResponse(description = "Lead encontrado",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = LeadDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Lead no encontrado con el correo electrónico proporcionado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getLeadByEmail(@Parameter(description = "Correo electrónico del lead", required = true) @PathVariable String email) {
        try {
            return new ResponseEntity<>(serviceLead.getLeadByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    new ErrorResponse("Error al buscar el lead con email '" + email + "' [" + e.getMessage() + "]", HttpStatus.NOT_FOUND.value()),
                    HttpStatus.NOT_FOUND
            );
        }
    }
}
