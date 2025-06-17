package peppertech.crm.api.Tasks.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa una tarea (tarjeta) dentro de una etapa del tablero Kanban.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("cards")
public class Card implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "Task subject cannot be empty")
    @Size(min = 3, max = 100, message = "Subject must be between 3 and 100 characters")
    @Field("title")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Field("description")
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("created_at")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("due_date")
    private Date dueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("reminder_date")
    private Date reminderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("closed_date")
    private Date closedDate;

    @Field("priority")
    private Priority priority;

    @Field("column")
    private ObjectId column;

    @Field("assigned_to")
    private ObjectId assignedTo;

    @Field("created_by")
    private ObjectId createdBy;

    @Field("modified_by")
    private ObjectId modifiedBy;

    @Field("tags")
    private List<ObjectId> tags = new ArrayList<>();

    @Field("files")
    private List<ObjectId> files = new ArrayList<>();

    @Field("comments")
    private List<ObjectId> comments = new ArrayList<>();

    @Field("subtasks")
    private List<ObjectId> subtasks = new ArrayList<>();
}
