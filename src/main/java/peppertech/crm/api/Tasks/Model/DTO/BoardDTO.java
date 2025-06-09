package peppertech.crm.api.Tasks.Model.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * Data Transfer Object para la entidad Board.
 * Utilizado para transportar información de un proyecto sin exponer toda la entidad.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {

    @Schema(description = "ID único del tablero, generado automáticamente por la base de datos.",
            example = "64a1f9b5e4b0f23d4c8a7e12", hidden = true)
    private String id;

    @Schema(description = "Nombre del tablero. Este campo es obligatorio.",
            example = "Proyecto Kanban")
    private String name;

    @Schema(description = "Descripción breve del proyecto o tablero.",
            example = "Proyecto para gestión de tareas en equipo.")
    private String description;

    @Schema(description = "Fecha de creación del tablero.",
            example = "2025-06-09T12:00:00.000Z", type = "string", format = "date-time")
    private Date createdAt;

    @Schema(description = "Fecha de inicio del proyecto.",
            example = "2025-06-10", type = "string", format = "date")
    private Date startDate;

    @Schema(description = "Fecha final estimada del proyecto.",
            example = "2025-12-31", type = "string", format = "date")
    private Date endDate;

    @Schema(description = "Estado actual del proyecto (ACTIVE, DELAYED, COMPLETED).",
            example = "ACTIVE")
    private String status;

    @Schema(description = "ID del propietario del tablero.",
            example = "64a1f9b5e4b0f23d4c8a7e10")
    private String ownerId;

    @Schema(description = "Lista de IDs de los administradores con permisos completos.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e11\", \"64a1f9b5e4b0f23d4c8a7e15\"]")
    private List<String> administratorIds;

    @Schema(description = "Lista de IDs de usuarios con permisos para editar.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e12\"]")
    private List<String> editorIds;

    @Schema(description = "Lista de IDs de usuarios con permisos solo de visualización.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e13\"]")
    private List<String> viewerIds;

    @Schema(description = "Lista de IDs de las columnas (fases) asociadas al tablero.",
            example = "[\"64a1f9b5e4b0f23d4c8a7e14\"]")
    private List<String> columnIds;

}
