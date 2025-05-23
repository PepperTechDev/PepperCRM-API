package peppertech.crm.api.Messages.Service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import peppertech.crm.api.Messages.Model.MessageDetails;

@Slf4j
@Service
public class MessageService implements MessageServiceI {
    private final RestTemplate restTemplate;
    @Value("${spring.telegram.bot.url}")
    private String telegramApiUrl;

    public MessageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Enviar un mensaje a un chat específico de Telegram.
     *
     * @param chatId  ID del chat o usuario de Telegram. #1515431025
     * @param message Contenido del mensaje a enviar.
     */
    @Async
    public void sendMessage(String chatId, String message) {
        try {
            String url = telegramApiUrl + "sendMessage";
            MessageDetails telegramMessage = new MessageDetails(chatId, message);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");

            HttpEntity<MessageDetails> request = new HttpEntity<>(telegramMessage, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Mensaje enviado correctamente a Telegram.");
            } else {
                log.warn("Error al enviar mensaje a Telegram: " + response.getBody());
            }
        } catch (Exception e) {
            log.error("Error al enviar mensaje a Telegram", e);
        }
    }
}
