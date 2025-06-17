package peppertech.crm.api.Tasks.Validator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import peppertech.crm.api.Tasks.Model.Entity.ProjectStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static peppertech.crm.api.Tasks.Validator.BoardRegex.NAME_PATTERN;

@Component
public class ValidatorBoard implements ValidatorBoardI {
    public boolean valid;
    public List<String> errors;

    /**
     * Resetea el estado de la validación, marcando el usuario como válido y limpiando la lista de errores.
     */
    @Override
    public void Reset() {
        valid = true;
        errors = new ArrayList<>();
    }

    public ValidatorBoard() {
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
        Reset();
        return currentErrors;
    }

    @Override
    public void validateId(String id) {
        if (id == null || id.isEmpty()) {
            errors.add("ID cannot be null or empty");
            valid = false;
        } else if (!ObjectId.isValid(id)) {
            errors.add("Id must be a 24-character hexadecimal");
            valid = false;
        }
    }

    @Override
    public void validateListId(List<String> listId) {
        if (listId == null || listId.isEmpty()) {
            errors.add("The list of Ids cannot be empty");
            valid = false;
        } else {
            for (String id : listId) {
                if (id == null || id.isEmpty() || !ObjectId.isValid(id)) {
                    errors.add("The Id '" + id + "' is not valid");
                    valid = false;
                }
            }
        }
    }

    /**
     * Válida el nombre de un usuario.
     * <p>
     * El nombre debe tener entre 4 y 15 caracteres y solo puede contener letras.
     * </p>
     *
     * @param name El nombre del usuario a validar.
     */
    @Override
    public void validateName(String name) {
        if (name == null || !Pattern.matches(NAME_PATTERN, name)) {
            valid = false;
            errors.add("The name must be between 4 and 15 characters and only letters");
        }
    }

    @Override
    public void validateDescription(String description) {
        if (description == null || description.isEmpty()) {
            errors.add("Description cannot be empty");
            valid = false;
        } else if (description.length() > 300) {
            errors.add("Description must not exceed 300 characters");
            valid = false;
        }
    }

    @Override
    public void validateDate(String date) {
        if (date == null || date.isEmpty()) {
            errors.add("Date cannot be empty");
            valid = false;
        } else {
            try {
                new java.text.SimpleDateFormat("yyyy-MM-dd").parse(date);
            } catch (java.text.ParseException e) {
                errors.add("Date must be in the format yyyy-MM-dd");
                valid = false;
            }
        }
    }

    @Override
    public void validateFutureDate(String date) {
        if (date == null || date.isEmpty()) {
            errors.add("Date cannot be empty");
            valid = false;
            return;
        }

        try {
            java.util.Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            if (parsedDate.before(new java.util.Date())) {
                errors.add("Date must be in the future");
                valid = false;
            }
        } catch (java.text.ParseException e) {
            errors.add("Date must be in the format yyyy-MM-dd");
            valid = false;
        }
    }


    /**
     * Validates a board's status.
     * <p>
     * The stauts must be one of the project status defined in the system. If the status is not valid, an error is added to the list.
     * </p>
     *
     * @param projectStatus The board's status to validate.
     */
    @Override
    public void validateStatus(String projectStatus) {
        if (projectStatus == null || projectStatus.isEmpty()) {
            errors.add("Status cannot be empty");
            valid = false;
            return;
        }

        Set<String> validProjectStatus = Arrays.stream(ProjectStatus.values())
                .map(Enum::name).collect(Collectors.toSet());

        if (!validProjectStatus.contains(projectStatus)) {
            errors.add("Status must be one of the following: " + String.join(", ", validProjectStatus));
            valid = false;
        }
    }
}
