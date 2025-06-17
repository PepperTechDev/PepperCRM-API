package peppertech.crm.api.Tasks.Validator;

public class BoardRegex {
    /**
     * Expresión regular para validar el nombre de un tablero Kanban.
     *
     * <p>
     * Esta expresión asegura que el nombre ingresado:
     * </p>
     * <ul>
     *   <li>Contenga únicamente letras (mayúsculas, minúsculas), incluyendo tildes y la letra ñ/Ñ.</li>
     *   <li>Permita espacios entre palabras.</li>
     *   <li>Tenga una longitud mínima de 4 y máxima de 30 caracteres.</li>
     * </ul>
     *
     * <p>
     * Corresponde a los criterios de aceptación de la historia de usuario:
     * </p>
     * <ul>
     *   <li><b>Escenario 1:</b> Permite crear un tablero cuando se proporciona un nombre válido.</li>
     *   <li><b>Escenario 2:</b> Impide la creación cuando el campo está vacío o contiene caracteres no válidos.</li>
     * </ul>
     *
     * <p>
     * Ejemplos válidos: "Tablero Uno", "Proyecto Ágil", "Planeación Ñu"<br>
     * Ejemplos inválidos:"", "123 Plan", "Plan#2025"
     * </p>
     */
    public static final String NAME_PATTERN = "^[a-zA-ZÁ-ÿñÑ ]{4,30}$";
}
