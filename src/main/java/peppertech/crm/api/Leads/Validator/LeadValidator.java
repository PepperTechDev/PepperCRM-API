package peppertech.crm.api.Leads.Validator;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static peppertech.crm.api.Leads.Validator.LeadRegex.*;

/**
 * Clase de validación para los leads.
 * <p>
 * Esta clase proporciona métodos para validar diferentes campos de un lead, como el ID, nombre, apellido, correo electrónico,
 * contraseña (si aplica) y rol (si aplica). Si algún campo no cumple con las restricciones definidas, se añaden errores a la lista.
 * </p>
 * <p>
 * Los métodos de validación devuelven los errores como una lista de cadenas y mantienen un estado de validez.
 * </p>
 */
@Component
public class LeadValidator implements LeadValidatorI {

    public boolean valid;
    public List<String> errors;

    public LeadValidator() {
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
            errors.add("El Id no puede estar vacío");
            valid = false;
        } else if (!ObjectId.isValid(id)) {
            errors.add("El Id debe ser un hexadecimal de 24 caracteres");
            valid = false;
        }
    }

    @Override
    public void Reset() {
        valid = true;
        errors = new ArrayList<>();
    }

    @Override
    public void validateName(String name) {
        if (name == null || !Pattern.matches(NAME_PATTERN, name)) {
            valid = false;
            errors.add("El nombre debe tener entre 4 y 15 caracteres y solo letras");
        }
    }

    @Override
    public void validateLastname(String lastname) {
        if (lastname == null || !Pattern.matches(LASTNAME_PATTERN, lastname)) {
            valid = false;
            errors.add("El apellido debe tener entre 4 y 30 caracteres");
        }
    }

    @Override
    public void validateEmail(String email) {
        if (email == null || !Pattern.matches(EMAIL_PATTERN, email)) {
            valid = false;
            errors.add("El correo electrónico no es válido");
        }
    }
}