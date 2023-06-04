package org.digital.project_dto.response_project_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.enity_statuses.ProjectStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCardDto {
    private String projectCodeName;
    private String projectName;
    private String description;
    private String projectStatus;
}
