package peppertech.crm.api.Tasks.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnDTO {
    private ObjectId id;
    private String name;
    private List<ObjectId> taskIds;
}
