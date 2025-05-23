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
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import peppertech.crm.api.Users.Model.Entity.User;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Representa un tablero de tareas estilo Kanban.
 * Incluye nombre, descripción, fecha de creación,
 * usuarios por rol y fases (columnas de tareas).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tableros")
public class Board implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "El nombre del tablero no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre debe tener entre 3 y 50 caracteres")
    @Field("name")
    private String name;

    @Size(max = 500, message = "La descripción no puede tener más de 500 caracteres")
    @Field("description")
    private String description;

    @NotNull(message = "La fecha de creación no puede ser nula")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Field("created_at")
    private Date createdAt;

    @NotNull(message = "El propietario no puede ser nulo")
    @DBRef
    @Field("owner")
    private User owner;

    @DBRef
    @Field("administrators")
    private List<User> administrators;

    @DBRef
    @Field("editors")
    private List<User> editors;

    @DBRef
    @Field("viewers")
    private List<User> viewers;

    @Field("phases")
    private List<ObjectId> phases;
}