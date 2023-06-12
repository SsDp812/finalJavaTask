package ru.digital.dto.project_dto.request_project_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.digital.commons.enity_statuses.ProjectStatus;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Project for searching")
public class SearchProjectDto {
    @Schema(description = "Text filter for project")
    @Size(max = 20,message = "search filter should be less than 20 characters")
    private String textFilter;
    @Schema(description = "Filter - project statuses")
    private List<ProjectStatus> status;
}
