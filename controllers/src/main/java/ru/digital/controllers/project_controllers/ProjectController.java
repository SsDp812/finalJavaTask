package ru.digital.controllers.project_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.digital.business.project_services.ProjectService;
import ru.digital.dto.project_dto.request_project_dto.ChangeProjectStatusDto;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.dto.project_dto.request_project_dto.SearchProjectDto;
import ru.digital.dto.project_dto.request_project_dto.UpdateProjectDto;
import ru.digital.dto.project_dto.response_project_dto.ProjectCardDto;

import java.util.List;

@RestController
@RequestMapping("/project")
@Tag(name = "ProjectController", description = "Controller for project")
public class ProjectController {
    private ProjectService service;

    @Autowired
    public ProjectController(ProjectService service) {
        this.service = service;
    }

    @Operation(summary = "Creating new project")
    @PostMapping(value = "/new")
    public ProjectCardDto createProject(@RequestBody @Valid CreateProjectDto dto) {
        return service.createNewProject(dto);
    }

    @Operation(summary = "Updating project")
    @PostMapping(value = "/update")
    public ProjectCardDto updateProject(@RequestBody @Valid UpdateProjectDto dto) {
        return service.changeProject(dto);
    }

    @Operation(summary = "Searching project by filter")
    @PostMapping(value = "/search")
    public List<ProjectCardDto> searchProject(@RequestBody @Valid SearchProjectDto dto) {
        return service.searchProject(dto);
    }

    @Operation(summary = "Changing project status")
    @PostMapping(value = "/changeStatus")
    public ProjectCardDto changeProjectStatus(@RequestBody @Valid ChangeProjectStatusDto dto) {
        return service.changeProjectStatus(dto);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        return ex.getMessage();
    }
}
