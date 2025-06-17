package peppertech.crm.api.Users.Validator;

import java.util.List;

/**
 * Interface for user data validation.
 * <p>
 * This interface defines the necessary methods to validate common user fields, such as ID, first name, last name,
 * email, password, and role. It is implemented by classes that provide the concrete validation logic, such as {@link UserValidatorValidator}.
 * </p>
 */
public interface UserValidatorI {

    /**
     * Validates a user's ID.
     * <p>
     * The ID must be a unique and valid identifier. This method checks that the ID is properly formed
     * according to established rules (for example, a 24-character hexadecimal identifier).
     * </p>
     *
     * @param id The unique identifier of the user to validate.
     */
    void validateId(String id);

    /**
     * Resets the validation state.
     * <p>
     * This method resets the validity state to "valid" and clears the error list.
     * </p>
     */
    void Reset();

    /**
     * Validates a user's first name.
     * <p>
     * The name must meet a specific pattern that may include a range of characters (for example, between 4 and 15 characters),
     * and must contain only letters.
     * </p>
     *
     * @param name The user's first name to validate.
     */
    void validateName(String name);

    /**
     * Validates a user's last name.
     * <p>
     * The last name must meet a specific pattern (for example, between 4 and 30 characters).
     * </p>
     *
     * @param lastname The user's last name to validate.
     */
    void validateLastname(String lastname);

    /**
     * Validates a user's email.
     * <p>
     * The email must follow a standard format pattern (for example, it must contain an "@" and a valid domain).
     * </p>
     *
     * @param email The user's email to validate.
     */
    void validateEmail(String email);

    /**
     * Validates a user's password.
     * <p>
     * The password must meet certain restrictions, such as a minimum length, at least one uppercase letter,
     * one lowercase letter, one number, and one special character.
     * </p>
     *
     * @param password The user's password to validate.
     */
    void validatePassword(String password);

    /**
     * Validates a user's role.
     * <p>
     * The role must be one of the predefined roles in the system. This method ensures that the provided role
     * matches the valid available roles.
     * </p>
     *
     * @param role The user's role to validate.
     */
    void validateRole(String role);

    /**
     * Returns the validity state.
     * <p>
     * This method returns <code>true</code> if all fields are valid (no errors), and <code>false</code>
     * if any validation error has been found.
     * </p>
     *
     * @return <code>true</code> if the user is valid, <code>false</code> if there are validation errors.
     */
    boolean isValid();

    /**
     * Gets the list of errors found during validation.
     * <p>
     * This method returns a list with the error messages generated during the validation of the fields.
     * </p>
     *
     * @return A list of strings with the error messages.
     */
    List<String> getErrors();
}