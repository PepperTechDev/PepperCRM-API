package peppertech.crm.api.Tasks.Validator;

public class BoardRegex {
    /**
     * Expresión regular para validar el nombre de un tablero.
     * <p>
     * El nombre debe estar compuesto únicamente por letras (mayúsculas y minúsculas),
     * incluyendo caracteres acentuados y especiales. La longitud debe ser de 4 a 30 caracteres.
     * </p>
     */
    public static final String NAME_PATTERN = "^[a-zA-ZÁ-ÿá-ÿ]{4,30}$";
}
