package peppertech.crm.api.Leads.Validator;

/**
 * Contiene las expresiones regulares para la validación de los campos de lead.
 * <p>
 * Esta clase define una serie de constantes que representan las expresiones regulares (regex) utilizadas
 * para validar diversos campos del lead, como el nombre, apellido, correo electrónico, teléfono e interés.
 * Estas expresiones se usan en los métodos de validación de la clase {@link LeadValidator} y otras clases relacionadas.
 * </p>
 */
public class LeadRegex {

    /**
     * Expresión regular para validar el nombre de un lead.
     * <p>
     * El nombre debe contener solo letras (mayúsculas o minúsculas), incluyendo caracteres acentuados y especiales.
     * La longitud permitida es de 4 a 15 caracteres.
     * </p>
     */
    public static final String NAME_PATTERN = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{4,15}$";

    /**
     * Expresión regular para validar el apellido de un lead.
     * <p>
     * El apellido debe contener solo letras (mayúsculas o minúsculas), incluyendo caracteres acentuados y especiales.
     * La longitud permitida es de 4 a 30 caracteres.
     * </p>
     */
    public static final String LASTNAME_PATTERN = "^[a-zA-ZÁÉÍÓÚáéíóúñÑ]{4,30}$";

    /**
     * Expresión regular para validar la dirección de correo electrónico de un lead.
     * <p>
     * El correo debe cumplir con el formato: texto@dominio.extensión. No se permiten espacios ni caracteres inválidos.
     * </p>
     */
    public static final String EMAIL_PATTERN = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";

    /**
     * Expresión regular para validar el número de teléfono de un lead.
     * <p>
     * El teléfono debe ser un número que comience por 3 (en Colombia) y contenga exactamente 10 dígitos.
     * </p>
     */
    public static final String PHONE_PATTERN = "^(3\\d{9})$";
}
