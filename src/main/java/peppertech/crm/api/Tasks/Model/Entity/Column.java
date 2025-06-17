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
import java.util.ArrayList;
import java.util.List;

/**
 * Representa una fase o columna en un tablero Kanban.
 * Cada columna contiene un nombre, una descripción opcional, un color,
 * y una lista de tarjetas (tareas) asociadas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("columns")
public class Column implements Serializable {

    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "Column name cannot be empty")
    @Size(min = 3, max = 50, message = "Column name must be between 3 and 50 characters")
    @Field("name")
    private String name;

    @Size(max = 300, message = "Description cannot exceed 300 characters")
    @Field("description")
    private String description;

    @Size(max = 10, message = "Color must be a valid hex value")
    @Field("color")
    private String color; // Example: "#1A73E8"

    @Field("board")
    private ObjectId board;

    @Field("cards")
    private List<ObjectId> cards = new ArrayList<>();
}
