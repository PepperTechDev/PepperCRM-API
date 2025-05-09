package peppertech.crm.api.Messages.Controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import peppertech.crm.api.Messages.Service.MessageServiceI;

@RestController
@RequestMapping("/Emails")
@Tag(name = "Correos", description = "Operaciones relacionadas con correos")
public class MessageController {
    private final MessageServiceI messageServiceI;

    /**
     * Constructor del controlador {@link MessageController}.
     * <p>Se utiliza la inyección de dependencias para asignar el servicio {@link MessageServiceI} que gestionará las operaciones
     * relacionadas con los mensajes.</p>
     *
     * @param messageServiceI El servicio que contiene la lógica de negocio para manejar mensajes.
     * @throws NullPointerException Si el servicio proporcionado es {@code null}.
     * @see messageServiceI
     */
    @Autowired
    public MessageController(MessageServiceI messageServiceI) {
        this.messageServiceI = messageServiceI;
    }
}