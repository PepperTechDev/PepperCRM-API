package peppertech.crm.api.Users.Controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
}
