package org.digital.project_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_dto.request_project_dto.SearchProjectDto;
import org.digital.project_dto.request_project_dto.UpdateProjectDto;
import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.services.project_services.ProjectService;
import org.digital.services.project_services.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/project")
@Tag(name = "ProjectController", description = "Controller for project")
public class ProjectController {
    private ProjectService service;

    @Autowired
    public ProjectController(ProjectServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Creating new project")
    @PostMapping(value = "/new",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectCardDto createProject(@RequestBody CreateProjectDto dto) throws Exception {
        return service.createNewProject(dto);
    }

    @Operation(summary = "Updating project")
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectCardDto updateProject(@RequestBody UpdateProjectDto dto) throws Exception {
        return service.changeProject(dto);
    }

    @Operation(summary = "Searching project by filter")

    @PostMapping(value = "/search",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectCardDto> searchProject(@RequestBody SearchProjectDto dto) throws Exception {
        return service.searchProject(dto);
    }

    @Operation(summary = "Changing project status")
    @PostMapping(value = "/changeStatus",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProjectCardDto changeProjectStatus(@RequestBody ChangeProjectStatusDto dto) throws Exception {
        return service.changeProjectStatus(dto);
    }
}
