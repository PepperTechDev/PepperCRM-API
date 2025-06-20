package peppertech.crm.api.Tags.Model.DTO;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO implements Serializable {

    @Schema(description = "Unique identifier of the tag, generated by the database",
            example = "676ae2a9b909de5f9607fcb6", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @Schema(description = "Name of the tag. This field is mandatory.",
            example = "Urgent")
    private String name;

    @Schema(description = "Optional description providing more details about the tag.",
            example = "Tasks that need immediate attention")
    private String description;

    @Schema(description = "Hex color code representing the tag color.",
            example = "#FF5733")
    private String color;
}