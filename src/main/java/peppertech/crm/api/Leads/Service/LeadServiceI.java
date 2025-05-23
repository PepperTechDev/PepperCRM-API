package peppertech.crm.api.Leads.Service;

import jakarta.validation.ValidationException;
import peppertech.crm.api.Leads.Model.DTO.LeadDTO;

import java.util.List;

public interface LeadServiceI {
    /**
     * Crea un nuevo lead en el sistema.
     *
     * @param leadDTO el objeto que contiene los datos del nuevo lead a crear.
     * @return el objeto {@link LeadDTO} del lead creado.
     * @throws ValidationException, IllegalStateException si el lead ya existe o si ocurre un error durante la creación.
     */
    LeadDTO createLead(LeadDTO leadDTO) throws ValidationException, IllegalStateException;

    /**
     * Obtiene todos los leads registrados en el sistema.
     *
     * @return una lista de objetos {@link LeadDTO} que representan a todos los leads.
     * @throws Exception si ocurre un error al obtener los leads.
     */
    List<LeadDTO> getAllLeads() throws Exception;

    /**
     * Obtiene un lead por su ID.
     *
     * @param id el identificador único del lead a obtener.
     * @return el objeto {@link LeadDTO} correspondiente al lead con el ID proporcionado.
     * @throws Exception si no se encuentra el lead o si ocurre un error.
     */
    LeadDTO getLeadById(String id) throws Exception;

    /**
     * Obtiene un lead por su correo electrónico.
     *
     * @param email el correo electrónico del lead a obtener.
     * @return el objeto {@link LeadDTO} correspondiente al lead con el correo electrónico proporcionado.
     * @throws Exception si no se encuentra el lead o si ocurre un error.
     */
    LeadDTO getLeadByEmail(String email) throws Exception;

    /**
     * Obtiene una lista de leads que coinciden con un nombre.
     *
     * @param name el nombre del lead a buscar.
     * @return una lista de objetos {@link LeadDTO} que coinciden con el nombre proporcionado.
     * @throws Exception si no se encuentran leads o si ocurre un error durante la búsqueda.
     */
    List<LeadDTO> getLeadsByName(String name) throws Exception;

    /**
     * Obtiene una lista de leads que coinciden con un apellido.
     *
     * @param lastname el apellido del lead a buscar.
     * @return una lista de objetos {@link LeadDTO} que coinciden con el apellido proporcionado.
     * @throws Exception si no se encuentran leads o si ocurre un error durante la búsqueda.
     */
    List<LeadDTO> getLeadsByLastname(String lastname) throws Exception;

    /**
     * Actualiza los datos de un lead.
     *
     * @param id          el identificador único del lead a actualizar.
     * @param updatedLead un objeto {@link LeadDTO} con los datos actualizados del lead.
     * @return el objeto {@link LeadDTO} con los datos del lead actualizado.
     * @throws Exception si no se encuentra el lead o si ocurre un error durante la actualización.
     */
    LeadDTO updateLead(String id, LeadDTO updatedLead) throws Exception;

    /**
     * Elimina un lead del sistema.
     *
     * @param id el identificador único del lead a eliminar.
     * @return un mensaje indicando que el lead fue eliminado.
     * @throws Exception si no se encuentra el lead o si ocurre un error durante la eliminación.
     */
    String deleteLead(String id) throws Exception;

}
