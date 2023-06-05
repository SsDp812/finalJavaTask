package org.digital.project_dto.request_project_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.digital.enity_statuses.ProjectStatus;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(description = "Project for searching")
public class SearchProjectDto {
    @Schema(description = "Text filter for project")
    private String textFilter;
    @Schema(description = "Filter - project statuses")
    private List<ProjectStatus> status;
}
