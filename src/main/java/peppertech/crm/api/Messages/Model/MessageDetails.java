package peppertech.crm.api.Messages.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDetails {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("text")
    private String text;
}
