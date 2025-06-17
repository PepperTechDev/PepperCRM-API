package peppertech.crm.api.Leads.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import peppertech.crm.api.Leads.Model.Entity.Lead;

import java.util.List;

/**
 * Repository interface for accessing Lead documents in MongoDB.
 */
public interface LeadRepository extends MongoRepository<Lead, ObjectId> {

    /**
     * Searches for a list of leads whose first name matches the provided pattern,
     * performing a case-insensitive search.
     *
     * @param name The name or pattern to search in the 'name' field of the lead.
     * @return A list of leads whose name matches the given pattern.
     */
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Lead> findByName(String name);

    /**
     * Searches for a list of leads whose last name matches the provided pattern,
     * performing a case-insensitive search.
     *
     * @param lastname The last name or pattern to search in the 'lastname' field of the lead.
     * @return A list of leads whose last name matches the given pattern.
     */
    @Query("{ 'lastname' : { $regex: ?0, $options: 'i' } }")
    List<Lead> findByLastname(String lastname);

    /**
     * Searches for a list of leads whose name and last name match the provided patterns,
     * performing a case-insensitive search.
     *
     * @param name     The name or pattern to search in the 'name' field.
     * @param lastname The last name or pattern to search in the 'lastname' field.
     * @return A list of leads matching both the name and last name patterns.
     */
    @Query("{ 'name' : { $regex: ?0, $options: 'i' }, 'lastname' : { $regex: ?1, $options: 'i' } }")
    List<Lead> findByFullName(String name, String lastname);

    /**
     * Searches for a list of leads whose email exactly matches the provided email.
     *
     * @param email The email to search for in the 'email' field of the lead.
     * @return A list of leads with the exact email match.
     */
    @Query("{ 'email' : ?0 }")
    List<Lead> findByEmail(String email);

    /**
     * Checks whether a lead exists with the specified email address.
     *
     * <p>Returns {@code true} if a lead exists with the given email address,
     * otherwise returns {@code false}.</p>
     *
     * @param email The email address to verify.
     * @return {@code true} if a lead with the given email exists, {@code false} otherwise.
     */
    boolean existsByEmail(String email);
}
