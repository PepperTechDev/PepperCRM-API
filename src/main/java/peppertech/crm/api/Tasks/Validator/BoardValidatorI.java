package peppertech.crm.api.Tasks.Validator;

import java.util.List;

public interface BoardValidatorI {
    void validateId(String id);

    void validateListId(List<String> listId);

    void Reset();

    void validateName(String name);

    boolean isValid();

    List<String> getErrors();

    void validateStatus(String status);

    void validateDate(String date);

    void validateDescription(String description);

    void validateFutureDate(String date);
}
