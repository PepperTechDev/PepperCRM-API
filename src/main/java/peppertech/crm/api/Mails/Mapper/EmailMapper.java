package peppertech.crm.api.Mails.Mapper;

import org.bson.types.ObjectId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import peppertech.crm.api.Mails.Model.DTO.EmailDTO;
import peppertech.crm.api.Mails.Model.Entity.EmailDetails;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    @Mapping(source = "id", target = "id", qualifiedByName = "stringToObjectId")
    EmailDetails toEntity(EmailDTO emailDTO);

    @Mapping(source = "id", target = "id", qualifiedByName = "objectIdToString")
    EmailDTO toDTO(EmailDetails emailDetails);

    @org.mapstruct.Named("objectIdToString")
    default String objectIdToString(ObjectId objectId) {
        return objectId != null ? objectId.toHexString() : null;
    }

    @org.mapstruct.Named("stringToObjectId")
    default ObjectId stringToObjectId(String id) {
        return id != null ? new ObjectId(id) : null;
    }
}