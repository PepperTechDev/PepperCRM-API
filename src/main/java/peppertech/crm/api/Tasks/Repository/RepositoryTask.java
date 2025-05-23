package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Tasks.Model.Entity.Task;
import peppertech.crm.api.Users.Model.Entity.User;

@Repository
public interface RepositoryTask extends MongoRepository<Task, ObjectId>{
}