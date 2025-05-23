package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Tasks.Model.Entity.Phase;
import peppertech.crm.api.Tasks.Model.Entity.Task;

@Repository
public interface RepositoryPhase extends MongoRepository<Phase, ObjectId> {
}
