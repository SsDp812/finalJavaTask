package org.digital.project_dto;


import lombok.Data;

@Data
public class CreateProjectDto {
    private String projectCodeName;
    private String projectName;
    private String description;
}
