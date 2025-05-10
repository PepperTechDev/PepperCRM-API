package peppertech.crm.api.Users.Validator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import peppertech.crm.api.Users.Model.Entity.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static peppertech.crm.api.Users.Validator.UserRegex.*;

/**
 * Clase de validación para los usuarios.
 * <p>
 * Esta clase proporciona métodos para validar diferentes campos de un usuario, como el ID, nombre, apellido, correo electrónico,
 * contraseña y rol. Si algún campo no cumple con las restricciones definidas, se añaden errores a la lista de errores.
 * </p>
 * <p>
 * Los métodos de validación devuelven los errores como una lista de cadenas y mantienen un estado de validez.
 * </p>
 */
@Component
public class UserValidator implements UserValidatorI {

    /**
     * Indica si la validación fue exitosa.
     */
    public boolean valid;

    /**
     * Lista de errores de validación.
     */
    public List<String> errors;

    /**
     * Constructor de la clase de validación de usuario.
     * Inicializa el estado de validación a "válido" y crea una lista vacía de errores.
     */
    public UserValidator() {
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    /**
     * Devuelve el estado de validez.
     *
     * @return <code>true</code> si el usuario es válido, <code>false</code> si hay errores de validación.
     */
    @Override
    public boolean isValid() {
        return valid;
    }

    /**
     * Obtiene la lista de errores encontrados durante la validación.
     *
     * @return Una lista de cadenas con los mensajes de error.
     */
    @Override
    public List<String> getErrors() {
        List<String> currentErrors = errors;
        Reset();
        return currentErrors;
    }

    /**
     * Válida el ID de un usuario.
     * <p>
     * El ID debe ser una cadena no vacía y debe ser un identificador hexadecimal válido de 24 caracteres.
     * </p>
     *
     * @param id El identificador único del usuario a validar.
     */
    @Override
    public void validateId(String id) {
        if (id == null || id.isEmpty()) {
            errors.add("The ID cannot be empty");
            valid = false;
        } else if (!ObjectId.isValid(id)) {
            errors.add("The ID must be a 24-character hexadecimal string");
            valid = false;
        }
    }

    /**
     * Resetea el estado de la validación, marcando el usuario como válido y limpiando la lista de errores.
     */
    @Override
    public void Reset() {
        valid = true;
        errors = new ArrayList<>();
    }

    /**
     * Válida el nombre de un usuario.
     * <p>
     * El nombre debe tener entre 4 y 15 caracteres y solo puede contener letras.
     * </p>
     *
     * @param name El nombre del usuario a validar.
     */
    @Override
    public void validateName(String name) {
        if (name == null || !Pattern.matches(NAME_PATTERN, name)) {
            valid = false;
            errors.add("The first name must have between 4 and 15 characters and contain only letters");
        }
    }

    /**
     * Válida el apellido de un usuario.
     * <p>
     * El apellido debe tener entre 4 y 30 caracteres.
     * </p>
     *
     * @param lastname El apellido del usuario a validar.
     */
    @Override
    public void validateLastname(String lastname) {
        if (lastname == null || !Pattern.matches(LASTNAME_PATTERN, lastname)) {
            valid = false;
            errors.add("The last name must have between 4 and 30 characters");
        }
    }

    /**
     * Válida el correo electrónico de un usuario.
     * <p>
     * El correo electrónico debe seguir un patrón de formato válido de correo electrónico.
     * </p>
     *
     * @param email El correo electrónico del usuario a validar.
     */
    @Override
    public void validateEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_PATTERN, email)) {
            valid = false;
            errors.add("The email is not valid");
        }
    }

    /**
     * Válida la contraseña de un usuario.
     * <p>
     * La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.
     * </p>
     *
     * @param password La contraseña del usuario a validar.
     */
    @Override
    public void validatePassword(String password) {
        if (password == null || !Pattern.matches(PASSWORD_PATTERN, password)) {
            valid = false;
            errors.add("The password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }

    /**
     * Válida el rol de un usuario.
     * <p>
     * El rol debe ser uno de los roles definidos en el sistema. Si el rol no es válido, se añade un error a la lista.
     * </p>
     *
     * @param role El rol del usuario a validar.
     */
    @Override
    public void validateRole(String role) {
        if (role == null || role.isEmpty()) {
            errors.add("The role cannot be empty");
            valid = false;
            return;
        }

        Set<String> validRoles = Arrays.stream(UserRole.values())
                .map(Enum::name).collect(Collectors.toSet());

        if (!validRoles.contains(role)) {
            errors.add("The role must be one of the following: " + String.join(", ", validRoles));
            valid = false;
        }
    }
}
