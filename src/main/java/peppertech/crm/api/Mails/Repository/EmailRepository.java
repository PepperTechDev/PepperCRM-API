package peppertech.crm.api.Mails.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Mails.Model.Entity.EmailDetails;

@Repository
public interface EmailRepository extends MongoRepository<EmailDetails, String> {

}
