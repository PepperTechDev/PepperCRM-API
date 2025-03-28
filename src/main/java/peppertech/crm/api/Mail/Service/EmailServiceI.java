package peppertech.crm.api.Mail.Service;

import peppertech.crm.api.Mail.Model.DTO.EmailDTO;
import peppertech.crm.api.Mail.Model.Entity.EmailDetails;

import java.util.List;

public interface EmailServiceI {

    void sendSimpleMail(EmailDetails details);

    void sendMailWithAttachment(EmailDetails details);

    List<EmailDTO> getAllMails() throws Exception;
}
