package ru.digital.business.project_services;

import ru.digital.dto.project_dto.request_project_dto.ChangeProjectStatusDto;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.dto.project_dto.request_project_dto.SearchProjectDto;
import ru.digital.dto.project_dto.request_project_dto.UpdateProjectDto;
import ru.digital.dto.project_dto.response_project_dto.ProjectCardDto;

import java.util.List;

public interface ProjectService {
    public ProjectCardDto createNewProject(CreateProjectDto dto);

    public ProjectCardDto changeProject(UpdateProjectDto dto);

    public List<ProjectCardDto> searchProject(SearchProjectDto dto);

    public ProjectCardDto changeProjectStatus(ChangeProjectStatusDto dto);

}
