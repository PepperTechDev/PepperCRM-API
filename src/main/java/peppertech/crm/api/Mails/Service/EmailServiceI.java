package peppertech.crm.api.Mails.Service;

import peppertech.crm.api.Mails.Model.DTO.EmailDTO;

import java.util.List;

public interface EmailServiceI {

    EmailDTO sendSimpleMail(EmailDTO emailDTO) ;

    EmailDTO sendMailWithAttachment(EmailDTO emailDTO);

    List<EmailDTO> getAllMails() throws Exception;
}
