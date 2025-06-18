package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import peppertech.crm.api.Tasks.Model.DTO.BoardDTO;
import peppertech.crm.api.Tasks.Model.Entity.Board;
import peppertech.crm.api.Tasks.Model.Entity.ProjectStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "stringToDate")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "stringToDate")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "stringToDate")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "stringToObjectId")
    @Mapping(source = "administrators", target = "administrators", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "editors", target = "editors", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "viewers", target = "viewers", qualifiedByName = "stringListToObjectIdList")
    @Mapping(source = "status", target = "status", qualifiedByName = "stringToProjectStatus")
    @Mapping(source = "columns", target = "columns", qualifiedByName = "stringListToObjectIdList")
    Board toEntity(BoardDTO dto);

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    @Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "dateToString")
    @Mapping(source = "startDate", target = "startDate", qualifiedByName = "dateToString")
    @Mapping(source = "endDate", target = "endDate", qualifiedByName = "dateToString")
    @Mapping(source = "owner", target = "owner", qualifiedByName = "objectIdToString")
    @Mapping(source = "administrators", target = "administrators", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "editors", target = "editors", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "viewers", target = "viewers", qualifiedByName = "objectIdListToStringList")
    @Mapping(source = "status", target = "status", qualifiedByName = "projectStatusToString")
    @Mapping(source = "columns", target = "columns", qualifiedByName = "objectIdListToStringList")
    BoardDTO toDTO(Board board);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @Named("stringListToObjectIdList")
    default java.util.List<ObjectId> stringListToObjectIdList(java.util.List<String> ids) {
        if (ids == null) return null;
        java.util.List<ObjectId> list = new java.util.ArrayList<>();
        for (String id : ids) {
            list.add(stringToObjectId(id));
        }
        return list;
    }

    @Named("objectIdListToStringList")
    default java.util.List<String> objectIdListToStringList(java.util.List<ObjectId> ids) {
        if (ids == null) return null;
        java.util.List<String> list = new java.util.ArrayList<>();
        for (ObjectId id : ids) {
            list.add(objectIdToString(id));
        }
        return list;
    }

    @Named("stringToDate")
    default Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(dateString);
    }

    @Named("dateToString")
    default String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(date);

    }

    @Named("stringToProjectStatus")
    default ProjectStatus stringToProjectStatus(String status) {
        if (status == null) return null;
        return ProjectStatus.valueOf(status.toUpperCase());
    }

    @Named("projectStatusToString")
    default String projectStatusToString(ProjectStatus status) {
        return status != null ? status.name() : null;
    }
}