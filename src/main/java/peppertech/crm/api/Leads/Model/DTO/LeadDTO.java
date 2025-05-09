package peppertech.crm.api.Leads.Model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LeadDTO {
    @Schema(description = "ID único del usuario, generado automáticamente por la base de datos.",
            example = "676ae2a9b909de5f9607fcb6", hidden = true)
    private String id = null;

    @Schema(description = "Nombre del usuario. Este campo es obligatorio.",
            example = "Juan")
    private String name;

    @Schema(description = "Apellido del usuario. Este campo es obligatorio.",
            example = "Pérez")
    private String lastname;

    @Schema(description = "Correo electrónico del usuario. Este campo es obligatorio y debe ser único.",
            example = "usuario@example.com")
    private String email;

    @Schema(description = "Fecha de creación del usuario. Este campo es generado automáticamente en el momento de la creación y es oculto en las respuestas API.",
            example = "2024-12-24 11:34:49", hidden = true)
    private String createAt = null;
}
