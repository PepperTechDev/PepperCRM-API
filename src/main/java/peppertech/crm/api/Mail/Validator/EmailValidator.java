package peppertech.crm.api.Mail.Validator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class EmailValidator implements EmailValidatorI{
    private boolean valid;
    private List<String> errors;

    private static final String EMAIL_PATTERN =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    private static final int SUBJECT_MAX_LENGTH = 100;
    private static final int BODY_MAX_LENGTH = 1000;

    public EmailValidator() {
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    @Override
    public void reset() {
        this.valid = true;
        this.errors = new ArrayList<>();
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public List<String> getErrors() {
        List<String> currentErrors = errors;
        reset();
        return currentErrors;
    }

    @Override
    public void validateId(String id) {
        if (id == null || id.isEmpty()) {
            errors.add("The ID cannot be empty.");
            valid = false;
        } else if (!ObjectId.isValid(id)) {
            errors.add("The ID must be a 24-character hexadecimal string.");
            valid = false;
        }
    }

    @Override
    public void validateRecipient(String recipient) {
        if (recipient == null || recipient.isEmpty()) {
            errors.add("The recipient cannot be empty.");
            valid = false;
        } else if (!Pattern.matches(EMAIL_PATTERN, recipient)) {
            errors.add("The recipient email is not valid.");
            valid = false;
        }
    }

    @Override
    public void validateSubject(String subject) {
        if (subject != null && subject.length() > SUBJECT_MAX_LENGTH) {
            errors.add("The subject cannot be more than " + SUBJECT_MAX_LENGTH + " characters.");
            valid = false;
        }
    }

    @Override
    public void validateMsgBody(String msgBody) {
        if (msgBody != null && msgBody.length() > BODY_MAX_LENGTH) {
            errors.add("The message cannot be more than " + BODY_MAX_LENGTH + " characters.");
            valid = false;
        }
    }

    @Override
    public void validateAttachment(String attachment) {
        if (attachment != null && !attachment.isEmpty()) {
            if (!attachment.startsWith("http://") && !attachment.startsWith("https://")) {
                errors.add("The attachment link must be a valid URL (http or https).");
                valid = false;
            }
        }
    }
}