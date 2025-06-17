package peppertech.crm.api.Users.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a user in the system.
 * <p>
 * This class contains the essential data of a user, such as first name, last name,
 * email, password, registration date, and role. It is used to store and retrieve
 * user information from the "users" collection in MongoDB.
 * <p>
 * The class includes validations to ensure data correctness before persistence.
 * Additionally, the password is encrypted using BCrypt before being stored in the database.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
@Document(collection = "users")
public class User implements Serializable {

    /**
     * TODO: Consider implementing a method to hash the password before storing it.
     * ! Ensure sensitive data such as passwords are properly protected.
     * ? What measures will be taken if a user forgets their password?
     */

    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * User's first name.
     * Must not be empty and must contain between 4 and 15 characters.
     */
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters")
    @Field("name")
    private String name;

    /**
     * User's last name.
     * Must not be empty and must contain between 4 and 30 characters.
     */
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 4, max = 30, message = "Last name must be between 4 and 30 characters")
    @Field("lastname")
    private String lastname;

    /**
     * User's email address.
     * Must not be empty, must be valid, and unique.
     */
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 14, max = 254, message = "Email must be between 14 and 254 characters")
    @Email(message = "Email must be valid")
    @Indexed(unique = true)
    @Field("email")
    private String email;

    /**
     * Date the user was registered.
     * Automatically set at the moment of creation.
     */
    @NotNull(message = "Registration date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Field("create_at")
    private Date createAt;

    /**
     * User's password.
     * Must not be empty and must comply with the security policy:
     * at least 8 characters, one uppercase letter, one lowercase letter,
     * one number, and one special character.
     */
    @NotEmpty(message = "Password cannot be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, include an uppercase letter, "
                    + "a lowercase letter, a number, and a special character.")
    @Field("password")
    private String password;

    /**
     * Role assigned to the user.
     * Must not be empty.
     */
    @NotEmpty(message = "Role cannot be empty")
    @Field("role")
    private UserRole userRole;
}
