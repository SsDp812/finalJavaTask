package ru.digital.dto.project_dto.request_project_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.digital.commons.enity_statuses.ProjectStatus;

@Data
@AllArgsConstructor
@Schema(description = "Project for change status")
public class ChangeProjectStatusDto {
    @Schema(description = "Project code name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "projectCodeName length should be between 5 and 25")
    private String projectCodeName;
    @Schema(description = "New available project status")
    private ProjectStatus newStatus;
}
