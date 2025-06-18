package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import peppertech.crm.api.Tasks.Model.Entity.Subtask;

public interface SubtaskRepository extends MongoRepository<Subtask, ObjectId> {
}
