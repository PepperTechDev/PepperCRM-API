package peppertech.crm.api.Tasks.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

/**
 * Representa una subtarea dentro de una Card del sistema Kanban.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subtask implements Serializable {

    @Field("id")
    private ObjectId id;

    @NotEmpty(message = "Subtask title cannot be empty")
    @Size(min = 3, max = 100, message = "Subtask title must be between 3 and 100 characters")
    @Field("title")
    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Field("created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Field("closed_date")
    private Date closedDate;

    @Field("priority")
    private Priority priority;

    @Field("assigned_to")
    private ObjectId assignedTo;

    @Field("card")
    private Card card;

}
