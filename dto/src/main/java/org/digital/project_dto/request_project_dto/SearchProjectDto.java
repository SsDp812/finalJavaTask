package org.digital.project_dto.request_project_dto;

import lombok.Data;
import org.digital.enity_statuses.ProjectStatus;

import java.util.List;

@Data
public class SearchProjectDto {
    private String textFilter;
    private List<ProjectStatus> status;
}
