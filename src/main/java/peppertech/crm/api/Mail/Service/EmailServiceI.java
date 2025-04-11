package peppertech.crm.api.Mail.Service;

import peppertech.crm.api.Mail.Model.DTO.EmailDTO;
import peppertech.crm.api.Mail.Model.Entity.EmailDetails;

import java.util.List;

public interface EmailServiceI {

    EmailDTO sendSimpleMail(EmailDTO emailDTO) ;

    EmailDTO sendMailWithAttachment(EmailDTO emailDTO);

    List<EmailDTO> getAllMails() throws Exception;
}
