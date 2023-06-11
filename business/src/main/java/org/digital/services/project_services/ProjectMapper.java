package org.digital.services.project_services;

import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.project_model.Project;

public class ProjectMapper {
    //Mapper for mapping project to ProjectCardDto
    public static ProjectCardDto getProjectCardDto(Project project) {
        ProjectCardDto projectDto = new ProjectCardDto();
        projectDto.setProjectName(project.getProjectName());
        projectDto.setProjectCodeName(project.getProjectCodeName());
        projectDto.setProjectStatus(project.getProjectStatus().toString());
        projectDto.setDescription(project.getDescription());
        return projectDto;
    }
}
