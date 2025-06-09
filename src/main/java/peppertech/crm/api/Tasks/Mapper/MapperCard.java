package peppertech.crm.api.Tasks.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import peppertech.crm.api.Tasks.Model.DTO.CardDTO;
import peppertech.crm.api.Tasks.Model.Entity.Card;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MapperCard {

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    @Mapping(source = "assignedTo.id", target = "assignedToId", qualifiedByName = "objectIdToString")
    CardDTO toDTO(Card card);

    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    @Mapping(source = "assignedToId", target = "assignedTo.id", qualifiedByName = "stringToObjectId")
    Card toEntity(CardDTO DTO);

    @Named("objectIdToString")
    default String objectIdToString(ObjectId id) {
        return id != null ? id.toHexString() : null;
    }

    @Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }

    @Named("dateToString")
    default String dateToString(Date date) {
        return date != null ? new SimpleDateFormat("yyyy-MM-dd").format(date) : null;
    }

    @Named("stringToDate")
    default Date stringToDate(String dateString) throws Exception {
        return dateString != null ? new SimpleDateFormat("yyyy-MM-dd").parse(dateString) : null;
    }

    @Named("stringListToObjectIdList")
    default List<ObjectId> stringListToObjectIdList(List<String> ids) {
        return ids != null ? ids.stream().map(ObjectId::new).collect(Collectors.toList()) : null;
    }

    @Named("objectIdListToStringList")
    default List<String> objectIdListToStringList(List<ObjectId> ids) {
        return ids != null ? ids.stream().map(ObjectId::toHexString).collect(Collectors.toList()) : null;
    }
}
