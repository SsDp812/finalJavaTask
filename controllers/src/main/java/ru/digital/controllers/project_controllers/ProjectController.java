package ru.digital.controllers.project_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectCardDto> createProject(@RequestBody @Valid CreateProjectDto dto) throws Exception {
        return ResponseEntity.ok(service.createNewProject(dto));
    }

    @Operation(summary = "Updating project")
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectCardDto updateProject(@RequestBody @Valid UpdateProjectDto dto) throws Exception {
        return service.changeProject(dto);
    }

    @Operation(summary = "Searching project by filter")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectCardDto> searchProject(@RequestBody @Valid SearchProjectDto dto) throws Exception {
        return service.searchProject(dto);
    }

    @Operation(summary = "Changing project status")
    @PostMapping(value = "/changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectCardDto changeProjectStatus(@RequestBody @Valid ChangeProjectStatusDto dto) throws Exception {
        return service.changeProjectStatus(dto);
    }
}
