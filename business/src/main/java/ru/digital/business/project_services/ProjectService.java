package ru.digital.business.project_services;

import ru.digital.dto.project_dto.request_project_dto.ChangeProjectStatusDto;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.dto.project_dto.request_project_dto.SearchProjectDto;
import ru.digital.dto.project_dto.request_project_dto.UpdateProjectDto;
import ru.digital.dto.project_dto.response_project_dto.ProjectCardDto;

import java.util.List;

public interface ProjectService {
    public ProjectCardDto createNewProject(CreateProjectDto dto) throws Exception;

    public ProjectCardDto changeProject(UpdateProjectDto dto) throws Exception;

    public List<ProjectCardDto> searchProject(SearchProjectDto dto) throws Exception;

    public ProjectCardDto changeProjectStatus(ChangeProjectStatusDto dto) throws Exception;

}
