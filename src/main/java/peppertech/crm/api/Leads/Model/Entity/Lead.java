package peppertech.crm.api.Leads.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes")
public class Lead {
    @Id
    @Field("_id")
    private ObjectId id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 4, max = 15, message = "El nombre debe tener entre 4 y 15 caracteres")
    @Field("name")
    private String name;

    @NotEmpty(message = "El apellido no puede estar vacío")
    @Size(min = 4, max = 30, message = "El apellido debe tener entre 4 y 30 caracteres")
    @Field("lastname")
    private String lastname;

    @NotEmpty(message = "El email no puede estar vacío")
    @Size(min = 14, max = 254, message = "El email debe tener entre 14 y 254 caracteres")
    @Email(message = "El email debe ser válido")
    @Indexed(unique = true)
    @Field("email")
    private String email;

    @NotNull(message = "La fecha de registro no puede estar vacía")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @Field("create_at")
    private Date createAt;
}
