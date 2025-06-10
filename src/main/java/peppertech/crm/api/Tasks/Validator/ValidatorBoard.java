package peppertech.crm.api.Tasks.Validator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import peppertech.crm.api.Tasks.Model.Entity.ProjectStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static peppertech.crm.api.Users.Validator.UserRegex.NAME_PATTERN;

@Component
public class ValidatorBoard implements ValidatorBoardI {
    public boolean valid;
    public List<String> errors;

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
            errors.add("v");
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
     * Resetea el estado de la validación, marcando el usuario como válido y limpiando la lista de errores.
     */
    @Override
    public void Reset() {
        valid = true;
        errors = new ArrayList<>();
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
            errors.add("El nombre debe tener entre 4 y 15 caracteres y solo letras");
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
