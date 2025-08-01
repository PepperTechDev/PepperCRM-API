package peppertech.crm.api.Leads.Model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * Data Transfer Object for transferring Lead data in API requests/responses.
 */
@Data
@RequiredArgsConstructor
public class LeadDTO implements Serializable {

    @Schema(
            description = "Unique ID of the user, automatically generated by the database.",
            example = "676ae2a9b909de5f9607fcb6",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String id = null;

    @Schema(
            description = "User's first name. This field is required.",
            example = "Juan"
    )
    private String name;

    @Schema(
            description = "User's last name. This field is required.",
            example = "Pérez"
    )
    private String lastname;

    @Schema(
            description = "User's email address. This field is required and must be unique.",
            example = "usuario@example.com"
    )
    private String email;

    @Schema(
            description = "User's creation date. This field is automatically generated upon creation and hidden in API responses.",
            example = "2024-12-24 11:34:49",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String createAt = null;
}
