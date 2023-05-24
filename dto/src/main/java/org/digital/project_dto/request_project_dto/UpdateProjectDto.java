package org.digital.project_dto.request_project_dto;


import lombok.Data;

@Data
public class UpdateProjectDto {
    private String projectCodeName;
    private String projectName;
    private String description;
}
