package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Tasks.Model.DTO.TaskDTO;
import peppertech.crm.api.Tasks.Model.Entity.Task;
import peppertech.crm.api.Users.Model.DTO.UserDTO;
import peppertech.crm.api.Users.Model.Entity.User;

import java.util.Optional;

@Repository
public interface RepositoryTask extends MongoRepository<Task, ObjectId>{
    TaskDTO getTaskById(String id) throws Exception;
}