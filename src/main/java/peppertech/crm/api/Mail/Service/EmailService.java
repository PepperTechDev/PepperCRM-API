package peppertech.crm.api.Mail.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import peppertech.crm.api.Mail.Mapper.EmailMapper;
import peppertech.crm.api.Mail.Model.DTO.EmailDTO;
import peppertech.crm.api.Mail.Repository.EmailRepository;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailService implements EmailServiceI {
    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;
    private final EmailMapper emailMapper;
    @Value("${spring.mail.username}")
    private String sender;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, EmailRepository emailRepository, EmailMapper emailMapper) {
        this.javaMailSender = javaMailSender;
        this.emailRepository = emailRepository;
        this.emailMapper = emailMapper;
    }

    @Override
    public EmailDTO sendSimpleMail(EmailDTO emailDTO) throws ValidationException {
        try {
            return Optional.of(emailDTO)
                    .map(emailMapper::toEntity)
                    .map(emailDetails -> {
                        SimpleMailMessage mailMessage = new SimpleMailMessage();
                        mailMessage.setFrom(sender);
                        mailMessage.setTo(emailDetails.getRecipient());
                        mailMessage.setText(emailDetails.getMsgBody());
                        mailMessage.setSubject(emailDetails.getSubject());
                        javaMailSender.send(mailMessage);
                        emailRepository.save(emailDetails);
                        return emailDetails;
                    })
                    .map(emailMapper::toDTO)
                    .orElseThrow(() -> new ValidationException("Failed to map email details"));
        } catch (Exception e) {
            log.error("Error sending email to {}: {}", emailDTO.getRecipient(), e.getMessage(), e);
            throw new ValidationException("Error sending email: " + e.getMessage());
        }
    }

    @Override
    public EmailDTO sendMailWithAttachment(EmailDTO emailDTO) throws ValidationException {
        try {
            return Optional.of(emailDTO)
                    .map(emailMapper::toEntity)
                    .map(emailDetails -> {
                        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                        MimeMessageHelper mimeMessageHelper;
                        try {
                            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
                            mimeMessageHelper.setFrom(sender);
                            mimeMessageHelper.setTo(emailDetails.getRecipient());
                            mimeMessageHelper.setText(emailDetails.getMsgBody());
                            mimeMessageHelper.setSubject(emailDetails.getSubject());
                            FileSystemResource file = new FileSystemResource(new File(emailDetails.getAttachment()));
                            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.getFilename()), file);
                            javaMailSender.send(mimeMessage);
                            emailRepository.save(emailDetails);
                        } catch (MessagingException e) {
                            log.error("Error sending email with attachment to {}: {}", emailDetails.getRecipient(), e.getMessage(), e);
                            throw new ValidationException("Error sending email with attachment: " + e.getMessage());
                        }
                        return emailDetails;
                    })
                    .map(emailMapper::toDTO)
                    .orElseThrow(() -> new ValidationException("Failed to map email details"));
        } catch (Exception e) {
            log.error("Error sending email with attachment: {}", e.getMessage(), e);
            throw new ValidationException("Error sending email with attachment: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los correos registrados en la base de datos y los convierte en objetos DTO.
     *
     * <p>Este método consulta todos los correos almacenados en la base de datos mediante el repositorio {@link EmailRepository}.
     * Si la lista de correos está vacía, se lanza una excepción. Los correos obtenidos se mapean a objetos
     * {@link EmailDTO} utilizando el convertidor {@link EmailMapper}.</p>
     *
     * @return Una lista de objetos {@link EmailDTO} que representan todos los correos en la base de datos.
     * @throws ValidationException Si no existen correos registrados en la base de datos.
     * @see EmailDTO
     * @see EmailRepository
     * @see EmailMapper
     */
    @Override
    @Cacheable(value = "mails", key = "'all_mails'")
    public List<EmailDTO> getAllMails() throws ValidationException {
        try {
            return Optional.of(emailRepository.findAll())
                    .filter(emails -> !emails.isEmpty())
                    .map(emails -> emails.stream()
                            .map(emailMapper::toDTO)
                            .collect(Collectors.toList()))
                    .orElseThrow(() -> new ValidationException("No emails found in the database"));
        } catch (Exception e) {
            log.error("Error fetching all emails: {}", e.getMessage(), e);
            throw new ValidationException("Error fetching all emails: " + e.getMessage());
        }
    }
}
