package ru.digital.dto.project_dto.request_project_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Project for creating")
public class CreateProjectDto {
    @Schema(description = "Project code name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "projectCodeName length should be between 5 and 25")
    private String projectCodeName;
    @Schema(description = "Project name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "project name length should be between 5 and 25")
    private String projectName;
    @Schema(description = "Project description")
    private String description;
}
