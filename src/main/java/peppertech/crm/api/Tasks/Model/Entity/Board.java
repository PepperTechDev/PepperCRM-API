package peppertech.crm.api.Tasks.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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
 * Represents a Kanban project (Board) within the system.
 * Contains information about its owner, access roles,
 * associated columns, dates, and overall project status
 */
/**
 * Represents a Kanban project (Board) in the system.
 * Contains information about the owner, access roles,
 * associated columns, dates, and overall project status.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "boards")
@Schema(description = "Entity representing a Kanban project in the system.")
public class Board implements Serializable {

    @Id
    @Field("_id")
    @Schema(description = "Unique identifier of the board, generated automatically by MongoDB.",
            example = "64a1f9b5e4b0f23d4c8a7e12", accessMode = Schema.AccessMode.READ_ONLY)
    private ObjectId id;

    @NotBlank(message = "Board name cannot be blank")
    @Size(min = 3, max = 50, message = "Board name must be between 3 and 50 characters")
    @Field("name")
    @Schema(description = "Name of the board. This field is required.",
            example = "Kanban Project")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Field("description")
    @Schema(description = "Brief description of the project or board.",
            example = "Project for team task management.")
    private String description;

    @NotNull(message = "Project status cannot be null")
    @Field("status")
    @Schema(description = "Current status of the project (e.g., ACTIVE, DELAYED, COMPLETED).",
            example = "ACTIVE")
    private ProjectStatus status;

    @PastOrPresent(message = "Creation date cannot be in the future")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("created_at")
    @Schema(description = "Timestamp when the board was created.",
            example = "2025-06-09T12:00:00.000Z", type = "string", format = "date-time", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("start_date")
    @Schema(description = "Start date of the project.",
            example = "2025-06-10", type = "string", format = "date")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Field("end_date")
    @Schema(description = "Estimated end date of the project.",
            example = "2025-12-31", type = "string", format = "date")
    private Date endDate;

    @NotNull(message = "Owner ID cannot be null")
    @Field("owner")
    @Schema(description = "ID of the user who owns the board.",
            example = "64a1f9b5e4b0f23d4c8a7e10")
    private ObjectId owner;

    @Field("administrators")
    @Schema(description = "List of user IDs who have full administrator permissions.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e11\"]")
    private List<@NotNull ObjectId> administrators = new ArrayList<>();

    @Field("editors")
    @Schema(description = "List of user IDs with edit permissions.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e12\"]")
    private List<@NotNull ObjectId> editors = new ArrayList<>();

    @Field("viewers")
    @Schema(description = "List of user IDs with view-only access.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e13\"]")
    private List<@NotNull ObjectId> viewers = new ArrayList<>();

    @Field("columns")
    @Schema(description = "List of column IDs associated with this board.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e14\"]")
    private List<@NotNull ObjectId> columns = new ArrayList<>();
}