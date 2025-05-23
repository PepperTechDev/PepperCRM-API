package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;
import peppertech.crm.api.Tasks.Model.Entity.Board;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MapperBoard {

    @Mapping(source = "owner.id", target = "ownerId", qualifiedByName = "objectIdToString")
    @Mapping(source = "administrators", target = "administratorIds", qualifiedByName = "userListToIdList")
    @Mapping(source = "editors", target = "editorIds", qualifiedByName = "userListToIdList")
    @Mapping(source = "viewers", target = "viewerIds", qualifiedByName = "userListToIdList")
    BoardDTO toDto(Board board);

    @Mapping(source = "ownerId", target = "owner.id", qualifiedByName = "stringToObjectId")
    Board toEntity(BoardDTO boardDto);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @Named("userListToIdList")
    default List<String> userListToIdList(List<? extends peppertech.crm.api.Users.Model.Entity.User> users) {
        return users != null ? users.stream().map(user -> user.getId().toHexString()).collect(Collectors.toList()) : null;
    }
}
