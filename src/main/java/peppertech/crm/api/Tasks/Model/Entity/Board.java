package peppertech.crm.api.Tasks.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
 * Representa un proyecto Kanban (Board) dentro del sistema.
 * Contiene información sobre su propietario, roles de acceso,
 * columnas asociadas, fechas y estado general del proyecto.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "boards")
public class Board implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "Board name cannot be empty")
    @Size(min = 3, max = 50, message = "Board name must be between 3 and 50 characters")
    @Field("name")
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Field("description")
    private String description;

    @NotNull(message = "Project status cannot be null")
    @Field("status")
    private ProjectStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Field("created_at")
    private Date createdAt;

    @NotNull(message = "Start date cannot be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Field("start_date")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Field("end_date")
    private Date endDate;

    @NotNull(message = "Owner cannot be null")
    @Field("owner")
    private ObjectId owner;

    @Field("administrators")
    private List<ObjectId> administrators = new ArrayList<>();

    @Field("editors")
    private List<ObjectId> editors = new ArrayList<>();

    @Field("viewers")
    private List<ObjectId> viewers = new ArrayList<>();

    @Field("columns")
    private List<ObjectId> columns = new ArrayList<>();
}
