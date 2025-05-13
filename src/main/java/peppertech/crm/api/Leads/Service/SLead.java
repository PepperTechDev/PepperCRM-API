package peppertech.crm.api.Leads.Service;

import jakarta.validation.ValidationException;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peppertech.crm.api.Leads.Mapper.MLead;
import peppertech.crm.api.Leads.Model.DTO.LeadDTO;
import peppertech.crm.api.Leads.Repository.RLead;
import peppertech.crm.api.Leads.Validator.VLeadI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Esta clase implementa los métodos de servicio para la gestión de leads.
 * Incluye la validación de campos y la interacción con el repositorio de leads.
 */
@Slf4j
@Data
@Builder
@Service
public class SLead implements SLeadI {

    private final RLead repositoryLead;
    private final MLead mapperLead;
    private final VLeadI validatorLead;

    /**
     * Constructor que inyecta las dependencias del servicio.
     *
     * @param repositoryLead repositorio que maneja las operaciones de base de datos.
     * @param mapperLead     convertidor que convierte entidades Lead a LeadDTO.
     * @param validatorLead  validador que valida los datos de lead.
     */
    @Autowired
    public SLead(RLead repositoryLead, MLead mapperLead, VLeadI validatorLead) {
        this.repositoryLead = repositoryLead;
        this.mapperLead = mapperLead;
        this.validatorLead = validatorLead;
    }

    /**
     * Crea un nuevo lead en la base de datos a partir de un objeto {@link LeadDTO}.
     *
     * @param leadDTO el objeto {@link LeadDTO} que contiene los datos del nuevo lead.
     * @return un objeto {@link LeadDTO} que representa al lead creado.
     * @throws IllegalStateException si el lead ya existe en la base de datos (por email).
     * @throws ValidationException   si las validaciones de los campos del lead no son exitosas.
     */
    @Override
    @Transactional
    @CachePut(value = "leads", key = "#leadDTO.id")
    public LeadDTO createLead(LeadDTO leadDTO) throws ValidationException, IllegalStateException {
        return Optional.of(leadDTO)
                .filter(dto -> !repositoryLead.existsByEmail(dto.getEmail()))
                .map(validDTO -> {
                    validatorLead.validateName(validDTO.getName());
                    validatorLead.validateLastname(validDTO.getLastname());
                    validatorLead.validateEmail(validDTO.getEmail());
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }
                    validatorLead.Reset();
                    return validDTO;
                })
                .map(dto -> {
                    dto.setCreateAt(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                    return dto;
                })
                .map(mapperLead::toEntity)
                .map(repositoryLead::save)
                .map(mapperLead::toDTO)
                .orElseThrow(() -> new IllegalStateException("El Lead ya existe"));
    }

    /**
     * Obtiene todos los leads de la base de datos y los convierte a DTOs.
     *
     * @return una lista de objetos {@link LeadDTO} que representan a todos los leads.
     * @throws Exception si no se encuentran leads en la base de datos.
     */
    @Override
    @Cacheable(value = "leads", key = "'all_leads'")
    public List<LeadDTO> getAllLeads() throws Exception {
        return Optional.of(repositoryLead.findAll())
                .filter(leads -> !leads.isEmpty())
                .map(leads -> leads.stream()
                        .map(mapperLead::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("No existe ningún lead"));
    }

    /**
     * Obtiene un lead de la base de datos a partir de su ID.
     *
     * @param id el ID del lead que se desea obtener.
     * @return un objeto {@link LeadDTO} que representa al lead encontrado.
     * @throws Exception           si el lead no existe o el ID no es válido.
     * @throws ValidationException si el ID no es válido según las reglas de validación.
     */
    @Override
    @Cacheable(value = "leads", key = "'id_'+#id")
    public LeadDTO getLeadById(String id) throws Exception {
        return Optional.of(id)
                .map(validId -> {
                    validatorLead.validateId(validId);
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }
                    validatorLead.Reset();
                    return new ObjectId(validId);
                })
                .map(repositoryLead::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(mapperLead::toDTO)
                .orElseThrow(() -> new Exception("El Lead no existe"));
    }

    /**
     * Obtiene un lead de la base de datos a partir de su correo electrónico.
     *
     * @param email el correo electrónico del lead que se desea obtener.
     * @return un objeto {@link LeadDTO} que representa al lead encontrado.
     * @throws Exception           si el lead no existe o el correo no es válido.
     * @throws ValidationException si el correo no es válido según las reglas de validación.
     */
    @Override
    @Cacheable(value = "leads", key = "'email_'+#email")
    public LeadDTO getLeadByEmail(String email) throws Exception {
        return Optional.of(email)
                .map(validEmail -> {
                    validatorLead.validateEmail(validEmail);
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }
                    validatorLead.Reset();
                    return validEmail;
                })
                .map(repositoryLead::findByEmail)
                .filter(leads -> !leads.isEmpty())
                .map(leads -> leads.get(0))
                .map(mapperLead::toDTO)
                .orElseThrow(() -> new Exception("El lead no existe"));
    }

    /**
     * Obtiene una lista de leads de la base de datos a partir de su nombre.
     *
     * @param name el nombre del lead que se desea obtener.
     * @return una lista de objetos {@link LeadDTO} que representan a los leads encontrados.
     * @throws Exception           si no existen leads con el nombre o si el nombre no es válido.
     * @throws ValidationException si el nombre no es válido según las reglas de validación.
     */
    @Override
    @Cacheable(value = "leads", key = "'name_'+#name")
    public List<LeadDTO> getLeadsByName(String name) throws Exception {
        return Optional.of(name)
                .map(validName -> {
                    validatorLead.validateName(validName);
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }
                    validatorLead.Reset();
                    return validName;
                })
                .map(repositoryLead::findByName)
                .filter(leads -> !leads.isEmpty())
                .map(leads -> leads.stream()
                        .map(mapperLead::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("No existe ningún lead con ese nombre"));
    }

    /**
     * Obtiene una lista de leads de la base de datos a partir de su apellido.
     *
     * @param lastname el apellido del lead o los leads que se desea obtener.
     * @return una lista de objetos {@link LeadDTO} que representan a los leads encontrados.
     * @throws Exception           si no existen leads con el apellido o si el apellido no es válido.
     * @throws ValidationException si el apellido no es válido según las reglas de validación.
     */
    @Override
    @Cacheable(value = "leads", key = "'lastname_'+#lastname")
    public List<LeadDTO> getLeadsByLastname(String lastname) throws Exception {
        return Optional.of(lastname)
                .map(validLastname -> {
                    validatorLead.validateLastname(validLastname);
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }
                    validatorLead.Reset();
                    return validLastname;
                })
                .map(repositoryLead::findByLastname)
                .filter(leads -> !leads.isEmpty())
                .map(leads -> leads.stream()
                        .map(mapperLead::toDTO)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new Exception("No existe ningún lead con ese apellido"));
    }

    /**
     * Actualiza la información de un lead en la base de datos.
     * <br><br>
     * Este método permite actualizar los detalles de un lead existente en la base de datos. Primero,
     * se busca el lead por su ID. Si el lead existe, se actualizan los campos del lead con los
     * valores proporcionados en el objeto {@link LeadDTO}. Luego, se validan los nuevos valores antes de
     * guardar el lead actualizado. Si alguno de los valores es inválido, se lanza una excepción de validación.
     * <br><br>
     * **Transacciones (Spring Boot):** Este método está marcado con la anotación
     * `@Transactional`, lo que significa que la operación de actualización se ejecutará dentro de una transacción de Spring.
     * Si ocurre algún error, la transacción se revertirá, asegurando que los cambios no se apliquen si algo falla en el proceso.
     * <br><br>
     * **Rollback:** Las excepciones que extienden `RuntimeException` causarán un rollback automático, mientras que
     * las excepciones comprobadas, como `ValidationException`, no harán que la transacción se revierta a menos que se
     * indique explícitamente lo contrario.
     *
     * @param id          el identificador del lead a actualizar.
     * @param updatedLead el objeto {@link LeadDTO} que contiene los nuevos valores para el lead.
     * @return un objeto {@link LeadDTO} con la información actualizada del lead.
     * @throws Exception           si el lead no se pudo actualizar debido a algún error general.
     * @throws ValidationException si alguno de los campos del lead proporcionado no es válido según las reglas de validación.
     * @see LeadDTO
     * @see ValidationException
     * @see Transactional
     */
    @Override
    @Transactional
    @CachePut(value = "leads", key = "'id:'+#id")
    public LeadDTO updateLead(String id, LeadDTO updatedLead) throws Exception {
        LeadDTO existingLead = getLeadById(id);
        return Optional.of(updatedLead)
                .map(dto -> {
                    existingLead.setName(dto.getName() != null ? dto.getName() : existingLead.getName());
                    existingLead.setLastname(dto.getLastname() != null ? dto.getLastname() : existingLead.getLastname());
                    existingLead.setEmail(dto.getEmail() != null ? dto.getEmail() : existingLead.getEmail());
                    return existingLead;
                })
                .map(dto -> {
                    validatorLead.validateName(dto.getName());
                    validatorLead.validateLastname(dto.getLastname());
                    validatorLead.validateEmail(dto.getEmail());
                    if (!validatorLead.isValid()) {
                        throw new ValidationException(validatorLead.getErrors().toString());
                    }

                    validatorLead.Reset();
                    return dto;
                })
                .map(mapperLead::toEntity)
                .map(repositoryLead::save)
                .map(mapperLead::toDTO)
                .orElseThrow(() -> new Exception("El lead no se pudo actualizar"));
    }

    /**
     * Elimina un lead de la base de datos.
     * <br><br>
     * Este método permite eliminar un lead de la base de datos utilizando su ID. Primero, se verifica
     * si el lead existe. Si el lead existe, se elimina de la base de datos. Si no se encuentra
     * el lead con el ID proporcionado, se lanza una excepción indicando que el lead no existe.
     * <br><br>
     * **Transacciones (Spring Boot):** Este método está marcado con `@Transactional`, lo que garantiza
     * que si ocurre algún error durante la eliminación (por ejemplo, si el lead no existe o si hay un fallo
     * en la base de datos), la transacción será revertida y no se eliminará el lead.
     * <br><br>
     * **Rollback:** Al igual que los otros métodos, si ocurre una excepción de tipo `RuntimeException`, Spring
     * realizará un rollback automáticamente para mantener la integridad de los datos.
     *
     * @param id el identificador del lead que se desea eliminar.
     * @return un mensaje indicando que el lead ha sido eliminado correctamente.
     * @throws Exception si el lead no existe o si ocurre un error durante el proceso de eliminación.
     * @see LeadDTO
     * @see Transactional
     */
    @Override
    @Transactional
    @CacheEvict(value = "leads", key = "'id_'+#id")
    public String deleteLead(String id) throws Exception {
        return Optional.of(getLeadById(id))
                .map(lead -> {
                    repositoryLead.deleteById(new ObjectId(lead.getId()));
                    return "El Lead con ID '" + id + "' fue eliminado.";
                })
                .orElseThrow(() -> new Exception("El Lead no existe."));
    }
}
