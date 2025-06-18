package peppertech.crm.api.Responses;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
@Schema(description = "Represents the response after a successful delete operation.")
public class DeleteResponse {

    @Schema(
            description = "Message indicating the status of the delete operation.",
            example = "The resource has been successfully deleted."
    )
    public String message;
}
