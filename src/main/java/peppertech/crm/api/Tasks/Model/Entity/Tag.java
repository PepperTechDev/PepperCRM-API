package peppertech.crm.api.Tasks.Model.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Represents a tag (label) to categorize tasks or cards.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tags")
public class Tag implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "Tag name cannot be empty")
    @Size(min = 2, max = 30, message = "Tag name must be between 2 and 30 characters")
    @Field("name")
    private String name;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    @Field("description")
    private String description;

    @Size(max = 10, message = "Color must be a valid hex value")
    @Field("color")
    private String color;  // Example: "#FF5733"
}