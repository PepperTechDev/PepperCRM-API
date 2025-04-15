package peppertech.crm.api.Leads.Validator;

import java.util.List;

/**
 * Interfaz para la validación de los datos de un lead.
 * <p>
 * Esta interfaz define los métodos necesarios para validar los campos comunes de un lead, como el ID, nombre, apellido,
 * correo electrónico, teléfono e interés. Implementada por clases que proporcionan la lógica de validación concreta, como {@link VLead}.
 * </p>
 */
public interface VLeadI {

    /**
     * Válida el ID de un lead.
     * <p>
     * El ID debe ser un identificador único y válido. Este método verifica que el ID esté correctamente formado
     * según las reglas establecidas (por ejemplo, un identificador hexadecimal de 24 caracteres).
     * </p>
     *
     * @param id El identificador único del lead a validar.
     */
    void validateId(String id);

    /**
     * Resetea el estado de la validación.
     * <p>
     * Este método restablece el estado de validez a "válido" y limpia la lista de errores.
     * </p>
     */
    void Reset();

    /**
     * Válida el nombre de un lead.
     * <p>
     * El nombre debe cumplir con un patrón específico que puede incluir un rango de caracteres (por ejemplo, entre 4 y 15 caracteres),
     * y solo debe contener letras.
     * </p>
     *
     * @param name El nombre del lead a validar.
     */
    void validateName(String name);

    /**
     * Válida el apellido de un lead.
     * <p>
     * El apellido debe cumplir con un patrón específico (por ejemplo, entre 4 y 30 caracteres).
     * </p>
     *
     * @param lastname El apellido del lead a validar.
     */
    void validateLastname(String lastname);

    /**
     * Válida el correo electrónico de un lead.
     * <p>
     * El correo electrónico debe seguir un patrón de formato estándar (por ejemplo, debe contener una "@" y un dominio válido).
     * </p>
     *
     * @param email El correo electrónico del lead a validar.
     */
    void validateEmail(String email);

    /**
     * Devuelve el estado de validez.
     * <p>
     * Este método devuelve <code>true</code> si todos los campos son válidos (sin errores), y <code>false</code>
     * si se ha encontrado algún error de validación.
     * </p>
     *
     * @return <code>true</code> si el lead es válido, <code>false</code> si hay errores de validación.
     */
    boolean isValid();

    /**
     * Obtiene la lista de errores encontrados durante la validación.
     * <p>
     * Este método devuelve una lista con los mensajes de error generados durante la validación de los campos.
     * </p>
     *
     * @return Una lista de cadenas con los mensajes de error.
     */
    List<String> getErrors();
}
