package ru.digital.dto.project_dto.request_project_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Project for creating")
public class CreateProjectDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "Project name")
    private String projectName;
    @Schema(description = "Project description")
    private String description;
}