package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Tasks.Model.Entity.Card;

@Repository
public interface CardRepository extends MongoRepository<Card, ObjectId>{

}