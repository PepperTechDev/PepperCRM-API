package peppertech.crm.api.Users.Controller;

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
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Service.SUserI;

/**
 * Controlador REST para las operaciones relacionadas con los usuarios.
 * <p>Este controlador proporciona una serie de métodos para gestionar usuarios en el sistema.</p>
 *
 * @see SUserI
 */
@RestController
@RequestMapping("/Users")
@CacheConfig(cacheNames = "users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {
    /**
     * TODO: Refactorizar métodos para lanzar excepciones personalizadas en lugar de manejar errores con try-catch.
     * TODO: Considerar lanzar una excepción `ConflictException` si el correo electrónico ya está registrado en el sistema por ejemplo.
     * ! Asegurarse de que el flujo de errores sea consistente y se maneje correctamente en el controlador global.
     * ? ¿Qué tipo de error se debe lanzar si el correo electrónico no está válido o es incorrecto (formato de email inválido)?
     */

    private final SUserI serviceUser;

    /**
     * Constructor del controlador {@link UserController}.
     * <p>Se utiliza la inyección de dependencias para asignar el servicio {@link SUserI} que gestionará las operaciones
     * relacionadas con los usuarios.</p>
     *
     * @param serviceUser El servicio que contiene la lógica de negocio para manejar usuarios.
     * @throws NullPointerException Si el servicio proporcionado es {@code null}.
     * @see SUserI
     */
    @Autowired
    public UserController(SUserI serviceUser) {
        this.serviceUser = serviceUser;
    }

    /**
     * Crea un nuevo usuario en el sistema con la información proporcionada.
     * <p>Recupera los datos del usuario desde el objeto {@link UserDTO} proporcionado en el cuerpo de la solicitud.</p>
     * <p>El correo electrónico debe ser único, y el sistema validará que los campos obligatorios como el nombre y el apellido estén completos.</p>
     * <p>Si se crea con éxito, se devolverá el usuario generado con el código HTTP 201.</p>
     * <p>En caso de error (por ejemplo, si el correo electrónico ya está registrado), se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param userDTO El objeto {@link UserDTO} que contiene la información del nuevo usuario.
     *                Este objeto debe contener todos los campos obligatorios, como nombre, apellido y correo electrónico.
     * @return Un objeto {@link ResponseEntity} con el usuario creado (código HTTP 201) o un mensaje de error (código HTTP 404).
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>201 Created</b>: El usuario fue creado correctamente y se retorna un objeto {@link UserDTO} con los datos del usuario.<br></li>
     *   <li><b>404 Not Found</b>: Error al generar el usuario, por ejemplo, si el correo electrónico ya está registrado o faltan datos obligatorios.<br></li>
     * </ul>
     */
    @PostMapping("/Create")
    @Operation(summary = "Crear un nuevo usuario",
            description = "Crea un nuevo usuario con la información proporcionada. "
                    + "El correo electrónico debe ser único y los campos obligatorios deben estar completos.",
            responses = {
                    @ApiResponse(description = "Usuario Creado",
                            responseCode = "201",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "403",
                            description = "No se pudo crear el usuario, error de validación",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(responseCode = "409",
                            description = "Conflicto al crear el usuario.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> createUser(
            @Parameter(description = "Datos del nuevo usuario que se va a crear. Debe incluir nombre, apellido y un correo electrónico único.",
                    required = true) @RequestBody UserDTO userDTO) {
        try {
            return new ResponseEntity<>(serviceUser.CreateUser(userDTO), HttpStatus.CREATED);
        } catch (ValidationException e) {
            return new ResponseEntity<>(new ErrorResponse("Error al crear el usuario [" + e.getMessage() + "]", HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(new ErrorResponse("Error al crear el usuario [" + e.getMessage() + "]", HttpStatus.CONFLICT.value()), HttpStatus.CONFLICT);
        }
    }

    /**
     * Recupera todos los usuarios registrados en el sistema.
     * <p>Este método devuelve una lista con todos los usuarios que están registrados en el sistema.</p>
     * <p>Si no se encuentran usuarios, se devolverá una lista vacía con el código HTTP 200.</p>
     * <p>En caso de error (por ejemplo, problemas con la conexión a la base de datos o un fallo en el servicio),
     * se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @return Un objeto {@link ResponseEntity} que contiene una lista de todos los usuarios registrados (código HTTP 200).
     * En caso de error, se devolverá un mensaje de error con el código HTTP 404.
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Se retorna una lista de objetos {@link UserDTO} con la información de todos los usuarios registrados.<br></li>
     *   <li><b>404 Not Found</b>: No se encontraron usuarios registrados o hubo un error al recuperar los datos.<br></li>
     * </ul>
     */
    @GetMapping("/All")
    @Operation(summary = "Obtener todos los usuarios",
            description = "Recupera todos los usuarios registrados en el sistema. "
                    + "En caso de no haber usuarios, se devolverá una excepción.",
            responses = {
                    @ApiResponse(description = "Usuarios encontrados",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)))),
                    @ApiResponse(responseCode = "404",
                            description = "No se encontraron usuarios registrados.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getAllUsers() {
        try {
            return new ResponseEntity<>(serviceUser.getAllUsers(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error al buscar los usuarios [" + e.getMessage() + "]", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Busca un usuario utilizando su ID único.
     * <p>Este método recupera un usuario de la base de datos utilizando su identificador único. Si el usuario con el ID proporcionado existe, se devolverá un objeto {@link UserDTO} con la información del usuario.</p>
     * <p>Si no se encuentra un usuario con el ID proporcionado, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param id El identificador único del usuario que se desea recuperar. Este parámetro debe ser el ID único del usuario.
     *           El ID es obligatorio para la búsqueda del usuario.
     * @return Un objeto {@link ResponseEntity} que contiene:
     *         <ul>
     *           <li>El usuario correspondiente al ID si se encuentra (código HTTP 200).</li>
     *           <li>Un mensaje de error si no se encuentra el usuario (código HTTP 404).</li>
     *         </ul>
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Si el usuario es encontrado, se retorna un objeto {@link UserDTO} con los detalles del usuario.<br></li>
     *   <li><b>404 Not Found</b>: Si no se encuentra el usuario con el ID proporcionado, se retorna un objeto {@link ErrorResponse} con un mensaje de error.<br></li>
     * </ul>
     */
    @GetMapping("/id/{id}")
    @Operation(summary = "Buscar usuario por ID",
            description = "Recupera un usuario utilizando su ID único. "
                    + "Si el usuario no existe, se devolverá un error con el código HTTP 404.",
            responses = {
                    @ApiResponse(description = "Usuario encontrado",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Usuario no encontrado con el ID especificado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getUserById(@PathVariable @Parameter(description = "ID único del usuario a buscar.", required = true) String id) {
        try {
            return new ResponseEntity<>(serviceUser.getUserById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error al buscar el usuario con id '" + id + "' [" + e.getMessage() + "]", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Busca un usuario por su dirección de correo electrónico único.
     * <p>Este método recupera un usuario del sistema utilizando su correo electrónico único. Si el usuario con el correo electrónico proporcionado existe, se devolverá un objeto {@link UserDTO} con la información del usuario.</p>
     * <p>Si no se encuentra un usuario con el correo electrónico proporcionado, se devolverá un mensaje de error con el código HTTP 404.</p>
     *
     * @param email La dirección de correo electrónico del usuario. Este parámetro debe ser único en el sistema y se utilizará para buscar al usuario.
     * @return Un objeto {@link ResponseEntity} que contiene:
     *         <ul>
     *           <li>El usuario correspondiente al correo electrónico proporcionado si se encuentra (código HTTP 200).</li>
     *           <li>Un mensaje de error si no se encuentra el usuario (código HTTP 404).</li>
     *         </ul>
     *
     * <p><b>Respuestas posibles:</b></p>
     * <ul>
     *   <li><b>200 OK</b>: Si el usuario es encontrado, se retorna un objeto {@link UserDTO} con los detalles del usuario.<br></li>
     *   <li><b>404 Not Found</b>: Si no se encuentra el usuario con el correo electrónico proporcionado, se retorna un objeto {@link ErrorResponse} con un mensaje de error.<br></li>
     * </ul>
     */
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar usuario por correo electrónico",
            description = "Recupera un usuario utilizando su correo electrónico único. "
                    + "Si no se encuentra el usuario, se devolverá un error.",
            responses = {
                    @ApiResponse(description = "Usuario encontrado",
                            responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
                    @ApiResponse(responseCode = "404",
                            description = "Usuario no encontrado con el correo electrónico proporcionado.",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            })
    public ResponseEntity<?> getUsersByEmail(@Parameter(description = "Correo electrónico del usuario", required = true) @PathVariable String email) {
        try {
            return new ResponseEntity<>(serviceUser.getUserByEmail(email), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ErrorResponse("Error al buscar el usuario con email '" + email + "' [" + e.getMessage() + "]", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }
}
