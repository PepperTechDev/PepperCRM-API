package peppertech.crm.api.Leads.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a lead entity stored in the "leads" collection in MongoDB.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "leads")
public class Lead implements Serializable {

    /**
     * Unique identifier of the lead.
     */
    @Id
    @Field("_id")
    private ObjectId id;

    /**
     * First name of the lead.
     * Must not be empty and must be between 4 and 15 characters.
     */
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 4, max = 15, message = "Name must be between 4 and 15 characters")
    @Field("name")
    private String name;

    /**
     * Last name of the lead.
     * Must not be empty and must be between 4 and 30 characters.
     */
    @NotEmpty(message = "Last name cannot be empty")
    @Size(min = 4, max = 30, message = "Last name must be between 4 and 30 characters")
    @Field("lastname")
    private String lastname;

    /**
     * Email address of the lead.
     * Must not be empty, must be valid, and unique in the collection.
     */
    @NotEmpty(message = "Email cannot be empty")
    @Size(min = 14, max = 254, message = "Email must be between 14 and 254 characters")
    @Email(message = "Email must be valid")
    @Indexed(unique = true)
    @Field("email")
    private String email;

    /**
     * Date the lead was registered.
     * Must not be null.
     * Formatted as ISO 8601 with milliseconds and timezone.
     */
    @NotNull(message = "Registration date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("create_at")
    private Date createAt;
}
