package peppertech.crm.api.Files.Model.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDTO {

    @Schema(description = "Unique identifier of the file.", example = "665cb2ae437a4a0eebc8d6f3", accessMode = Schema.AccessMode.READ_ONLY)
    private String id;

    @NotBlank(message = "File name must not be blank.")
    @Size(max = 255, message = "File name must be less than 255 characters.")
    @Schema(description = "Original file name with extension.", example = "report-final.pdf")
    private String name;

    @NotBlank(message = "Content type must not be blank.")
    @Schema(description = "MIME type of the file.", example = "application/pdf")
    private String contentType;

    @NotNull(message = "File size must not be null.")
    @Schema(description = "Size of the file in bytes.", example = "204800")
    private Long size;

    @Schema(description = "Optional description of the file.", example = "Quarterly financial report.")
    @Size(max = 500, message = "Description must be less than 500 characters.")
    private String description;

    @NotBlank(message = "Storage path is required.")
    @Schema(description = "Path or URL where the file is stored.", example = "/files/2025/06/report-final.pdf")
    private String storagePath;

    @Schema(description = "IDs of related entities (e.g. task, comment, board).", example = "[\"665cb2ae437a4a0eebc8d6f5\"]")
    private List<String> relatedToIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date when the file was uploaded.", example = "2025-06-09 12:34:56", type = "string", format = "date-time", accessMode = Schema.AccessMode.READ_ONLY)
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date of the last update to the file metadata.", example = "2025-06-10 08:30:00", type = "string", format = "date-time", accessMode = Schema.AccessMode.READ_ONLY)
    private Date updatedAt;

    @Schema(description = "ID of the user who uploaded the file.", example = "665cb2ae437a4a0eebc8d6f1", accessMode = Schema.AccessMode.READ_ONLY)
    private String uploadedBy;
}