package peppertech.crm.api.Messages.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Document(collection = "messages")
public class MessageDetails implements Serializable {
    @JsonProperty("chat_id")
    private String chatId;

    @JsonProperty("text")
    private String text;
}
