package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;
import peppertech.crm.api.Tasks.Model.Entity.Card;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    @Mapping(source = "assignedTo", target = "assignedTo", qualifiedByName = "objectIdToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "dateToString")
    @Mapping(source = "dueDate", target = "dueDate", qualifiedByName = "dateToString")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "dateToString")
    @Mapping(source = "subtasks", target = "subtasks", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "objectIdToString")
    @Mapping(source = "modifiedBy", target = "modifiedBy", qualifiedByName = "objectIdToString")
    @Mapping(source = "column", target = "column", qualifiedByName = "objectIdToString")
    @Mapping(source = "files", target = "files", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "comments", target = "comments", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "objectIdListToStringList")
    CardDTO toDTO(Card card);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    @Mapping(source = "assignedTo", target = "assignedTo", qualifiedByName = "stringToObjectId")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToDate")
    @Mapping(source = "dueDate", target = "dueDate", qualifiedByName = "stringToDate")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "stringToDate")
    @Mapping(source = "subtasks", target = "subtasks", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "createdBy", target = "createdBy", qualifiedByName = "stringToObjectId")
    @Mapping(source = "modifiedBy", target = "modifiedBy", qualifiedByName = "stringToObjectId")
    @Mapping(source = "column", target = "column", qualifiedByName = "stringToObjectId")
    @Mapping(source = "files", target = "files", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "comments", target = "comments", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "tags", target = "tags", qualifiedByName = "stringListToObjectIdList")
    Card toEntity(CardDTO DTO);

    @org.mapstruct.Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @org.mapstruct.Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @org.mapstruct.Named("dateToString")
    default String dateToString(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd").format(date) : null;
    }

    @org.mapstruct.Named("stringToDate")
    default Date stringToDate(String dateString) throws Exception {
        return dateString != null ? new SimpleDateFormat("yyyy-MM-dd").parse(dateString) : null;
    }

    @org.mapstruct.Named("stringListToObjectIdList")
    default List<ObjectId> stringListToObjectIdList(List<String> ids) {
        return ids != null ? ids.stream().map(ObjectId::new).collect(Collectors.toList()) : null;
    }

    @org.mapstruct.Named("objectIdListToStringList")
    default List<String> objectIdListToStringList(List<ObjectId> ids) {
        return ids != null ? ids.stream().map(ObjectId::toHexString).collect(Collectors.toList()) : null;
    }
}
