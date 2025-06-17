package peppertech.crm.api.Tasks.Repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import peppertech.crm.api.Tasks.Model.Entity.Board;

import java.util.List;

@Repository
public interface BoardRepository extends MongoRepository<Board, ObjectId> {
    // Buscar tableros por nombre (insensible a mayúsculas/minúsculas)
    @Query("{ 'name' : { $regex: ?0, $options: 'i' } }")
    List<Board> findByName(String name);

    // Buscar tableros por owner
    List<Board> findByOwner(ObjectId owner);

    // Buscar tableros por estado
    List<Board> findByStatus(String status);

}
