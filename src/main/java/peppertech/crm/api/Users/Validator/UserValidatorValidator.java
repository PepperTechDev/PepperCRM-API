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
 * Validation class for users.
 * <p>
 * This class provides methods to validate different user fields, such as ID, name, lastname, email,
 * password, and role. If any field does not meet the defined restrictions, errors are added to the error list.
 * </p>
 * <p>
 * The validation methods return errors as a list of strings and maintain a validity state.
 * </p>
 */
@Component
public class UserValidatorValidator implements UserValidatorI {

    /**
     * Indicates if the validation was successful.
     */
    public boolean valid;

    /**
     * List of validation errors.
     */
    public List<String> errors;

    /**
     * Constructor for the user validation class.
     * Initializes the validation state as "valid" and creates an empty error list.
     */
    public UserValidatorValidator() {
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    /**
     * Returns the validity state.
     *
     * @return <code>true</code> if the user is valid, <code>false</code> if there are validation errors.
     */
    @Override
    public boolean isValid() {
        return valid;
    }

    /**
     * Gets the list of errors found during validation.
     *
     * @return A list of strings with the error messages.
     */
    @Override
    public List<String> getErrors() {
        List<String> currentErrors = errors;
        Reset();
        return currentErrors;
    }

    /**
     * Validates a user's ID.
     * <p>
     * The ID must be a non-empty string and a valid 24-character hexadecimal identifier.
     * </p>
     *
     * @param id The unique identifier of the user to validate.
     */
    @Override
    public void validateId(String id) {
        if (id == null || id.isEmpty()) {
            errors.add("Id cannot be empty");
            valid = false;
        } else if (!ObjectId.isValid(id)) {
            errors.add("Id must be a 24-character hexadecimal");
            valid = false;
        }
    }

    /**
     * Resets the validation state, marking the user as valid and clearing the error list.
     */
    @Override
    public void Reset() {
        valid = true;
        errors = new ArrayList<>();
    }

    /**
     * Validates a user's name.
     * <p>
     * The name must be between 4 and 15 characters and contain only letters.
     * </p>
     *
     * @param name The user's name to validate.
     */
    @Override
    public void validateName(String name) {
        if (name == null || !Pattern.matches(NAME_PATTERN, name)) {
            valid = false;
            errors.add("Name must be between 4 and 15 characters and only letters");
        }
    }

    /**
     * Validates a user's lastname.
     * <p>
     * The lastname must be between 4 and 30 characters.
     * </p>
     *
     * @param lastname The user's lastname to validate.
     */
    @Override
    public void validateLastname(String lastname) {
        if (lastname == null || !Pattern.matches(LASTNAME_PATTERN, lastname)) {
            valid = false;
            errors.add("Lastname must be between 4 and 30 characters");
        }
    }

    /**
     * Validates a user's email.
     * <p>
     * The email must follow a valid email format pattern.
     * </p>
     *
     * @param email The user's email to validate.
     */
    @Override
    public void validateEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_PATTERN, email)) {
            valid = false;
            errors.add("Email is not valid");
        }
    }

    /**
     * Validates a user's password.
     * <p>
     * The password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character.
     * </p>
     *
     * @param password The user's password to validate.
     */
    @Override
    public void validatePassword(String password) {
        if (password == null || !Pattern.matches(PASSWORD_PATTERN, password)) {
            valid = false;
            errors.add("Password must have at least 8 characters, one uppercase letter, one lowercase letter, one number, and one special character");
        }
    }

    /**
     * Validates a user's role.
     * <p>
     * The role must be one of the roles defined in the system. If the role is not valid, an error is added to the list.
     * </p>
     *
     * @param role The user's role to validate.
     */
    @Override
    public void validateRole(String role) {
        if (role == null || role.isEmpty()) {
            errors.add("Role cannot be empty");
            valid = false;
            return;
        }

        Set<String> validRoles = Arrays.stream(UserRole.values())
                .map(Enum::name).collect(Collectors.toSet());

        if (!validRoles.contains(role)) {
            errors.add("Role must be one of the following: " + String.join(", ", validRoles));
            valid = false;
        }
    }
}