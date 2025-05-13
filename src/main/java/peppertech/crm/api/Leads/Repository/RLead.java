package peppertech.crm.api.Leads.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import peppertech.crm.api.Leads.Model.Entity.Lead;

import java.util.List;

public interface RLead extends MongoRepository<Lead, ObjectId> {
    /**
     * Busca una lista de leads cuyo nombre coincida con el patrón proporcionado,
     * realizando una búsqueda insensible a mayúsculas/minúsculas.
     *
     * @param name El nombre o patrón a buscar en el campo 'name' del lead.
     * @return Una lista de leads cuyo nombre coincida con el patrón.
     */
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Lead> findByName(String name);

    /**
     * Busca una lista de leads cuyo apellido coincida con el patrón proporcionado,
     * realizando una búsqueda insensible a mayúsculas/minúsculas.
     *
     * @param lastname El apellido o patrón a buscar en el campo 'lastname' del lead.
     * @return Una lista de leads cuyo apellido coincida con el patrón.
     */
    @Query("{ 'lastname' : { $regex: ?0, $options: 'i' } }")
    List<Lead> findByLastname(String lastname);

    /**
     * Busca una lista de leads cuyo nombre y apellido coincidan con los patrones
     * proporcionados, realizando una búsqueda insensible a mayúsculas/minúsculas.
     *
     * @param name     El nombre o patrón a buscar en el campo 'name' del lead.
     * @param lastname El apellido o patrón a buscar en el campo 'lastname' del lead.
     * @return Una lista de leads cuyo nombre y apellido coincidan con los patrones.
     */
    @Query("{ 'name' : { $regex: ?0, $options: 'i' }, 'lastname' : { $regex: ?1, $options: 'i' } }")
    List<Lead> findByFullName(String name, String lastname);

    /**
     * Busca una lista de leads cuyo correo electrónico coincida exactamente
     * con el correo proporcionado.
     *
     * @param email El correo electrónico a buscar en el campo 'email' del lead.
     * @return Una lista de leads cuyo correo electrónico coincida exactamente.
     */
    @Query("{ 'email' : ?0 }")
    List<Lead> findByEmail(String email);

    /**
     * Verifica si existe un lead con el correo electrónico proporcionado.
     *
     * <p>Este método devuelve {@code true} si existe un lead con el correo electrónico especificado,
     * y {@code false} en caso contrario.</p>
     *
     * @param email El correo electrónico a verificar.
     * @return {@code true} si existe un lead con el correo electrónico proporcionado, {@code false} en caso contrario.
     */
    boolean existsByEmail(String email);
}
