package ru.digital.dto.project_dto.request_project_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Project for change status")
public class ChangeProjectStatusDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "New available project status")
    private String newStatus;
}
