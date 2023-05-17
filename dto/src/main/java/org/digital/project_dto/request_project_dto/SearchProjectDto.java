package org.digital.project_dto.request_project_dto;

import lombok.Data;
import org.digital.enity_statuses.ProjectStatus;

@Data
public class SearchProjectDto {
    private String name;
    private ProjectStatus status;
}
