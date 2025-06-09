package peppertech.crm.api.Files.Model.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

/**
 * Entity representing a file stored in the system.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "files")
public class File {

    @Id
    @Schema(description = "Unique identifier of the file.", example = "665cb2ae437a4a0eebc8d6f3")
    private String id;

    @Field("name")
    @NotBlank(message = "File name must not be blank.")
    @Size(max = 255, message = "File name must be less than 255 characters.")
    @Schema(description = "Original file name including extension.", example = "document.pdf", required = true)
    private String name;

    @Field("content_type")
    @NotBlank(message = "Content type must not be blank.")
    @Schema(description = "MIME type of the file.", example = "application/pdf", required = true)
    private String contentType;

    @Field("size")
    @NotNull(message = "File size must not be null.")
    @Schema(description = "Size of the file in bytes.", example = "1048576", required = true)
    private Long size;

    @Field("uploaded_by")
    @NotBlank(message = "Uploader ID is required.")
    @Schema(description = "User ID who uploaded the file.", example = "665cb2ae437a4a0eebc8d6f1")
    private String uploadedBy;

    @Field("created_at")
    @CreatedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Schema(description = "Date the file was uploaded.", example = "2025-06-09T12:34:56.789Z", type = "string", format = "date-time")
    private Date createdAt;

    @Field("updated_at")
    @LastModifiedDate
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    @Schema(description = "Date of the last update of the file metadata.", example = "2025-06-10T08:30:00.000Z", type = "string", format = "date-time")
    private Date updatedAt;

    @Field("created_by")
    @CreatedBy
    @Schema(description = "User who created this file record.", example = "admin@crm.com")
    private String createdBy;

    @Field("modified_by")
    @LastModifiedBy
    @Schema(description = "User who last modified this file record.", example = "editor@crm.com")
    private String modifiedBy;

    @Field("storage_path")
    @NotBlank(message = "Storage path is required.")
    @Schema(description = "Storage path or URL to retrieve the file.", example = "/files/2025/06/document.pdf")
    private String storagePath;

    @Field("description")
    @Size(max = 500, message = "Description must be under 500 characters.")
    @Schema(description = "Optional description about the file.", example = "Final version of the contract signed by both parties.")
    private String description;

    @Field("related_to_ids")
    @Schema(description = "IDs of related entities (e.g., task, comment, board).", example = "[\"665cb2ae437a4a0eebc8d6f5\"]")
    private List<String> relatedToIds;

}