package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import peppertech.crm.api.Tasks.Model.DTO.PhaseDTO;
import peppertech.crm.api.Tasks.Model.Entity.Phase;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MapperPhase {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    PhaseDTO toDto(Phase phase);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    Phase toEntity(PhaseDTO dto);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @Named("objectIdListToStringList")
    default List<String> objectIdListToStringList(List<ObjectId> ids) {
        return ids != null ? ids.stream().map(ObjectId::toHexString).collect(Collectors.toList()) : null;
    }

    @Named("stringListToObjectIdList")
    default List<ObjectId> stringListToObjectIdList(List<String> ids) {
        return ids != null ? ids.stream().map(ObjectId::new).collect(Collectors.toList()) : null;
    }
}
