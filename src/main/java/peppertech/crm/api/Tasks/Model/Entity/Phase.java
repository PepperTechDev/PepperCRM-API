package peppertech.crm.api.Tasks.Model.Entity;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

/**
 * Representa una fase o columna en un tablero Kanban.
 * Incluye un nombre y una lista de tareas asociadas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Phase implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "El nombre de la fase no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de la fase debe tener entre 3 y 50 caracteres")
    @Field("name")
    private String name;

    @DBRef
    @Field("tasks")
    private List<ObjectId> tasks;
}
