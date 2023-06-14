package ru.digital.dto.project_dto.response_project_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectCardDto {
    private String projectCodeName;
    private String projectName;
    private String description;
    private String projectStatus;
}
