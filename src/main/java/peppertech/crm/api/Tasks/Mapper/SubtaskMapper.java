package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import peppertech.crm.api.Tasks.Model.DTO.SubtaskDTO;
import peppertech.crm.api.Tasks.Model.Entity.Subtask;

import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface SubtaskMapper {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    @Mapping(source = "assignedTo", target = "assignedTo", qualifiedByName = "objectIdToString")
    @Mapping(source = "card", target = "card", qualifiedByName = "objectIdToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "dateToString")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "dateToString")
    SubtaskDTO toDTO(Subtask subtask);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    @Mapping(source = "assignedTo", target = "assignedTo", qualifiedByName = "stringToObjectId")
    @Mapping(source = "card", target = "card", qualifiedByName = "stringToObjectId")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToDate")
    @Mapping(source = "closedDate", target = "closedDate", qualifiedByName = "stringToDate")
    Subtask toEntity(SubtaskDTO dto);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @Named("dateToString")
    default String dateToString(Date date) {
        if (date == null) return null;
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").format(date);
    }

    @Named("stringToDate")
    default Date stringToDate(String date) {
        try {
            return date != null ? new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX").parse(date) : null;
        } catch (Exception e) {
            return null;
        }
    }
}