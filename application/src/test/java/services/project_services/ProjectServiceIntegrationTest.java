package services.project_services;

import org.digital.Main;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_dto.request_project_dto.SearchProjectDto;
import org.digital.project_dto.request_project_dto.UpdateProjectDto;
import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.project_model.Project;
import org.digital.services.project_services.ProjectServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import services.BaseTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;


@SpringBootTest(classes = Main.class)
public class ProjectServiceIntegrationTest extends BaseTest {
    @Autowired
    private ProjectServiceImpl service;


    @Test
    public void createProject() throws Exception {
        Project project = getSomeProject();
        ProjectCardDto dto = service.createNewProject(getCreateDto(project));
        Assertions.assertEquals(project.getProjectCodeName(), dto.getProjectCodeName());
        Assertions.assertEquals(project.getProjectName(), dto.getProjectName());
        Assertions.assertEquals(project.getDescription(), dto.getDescription());
        Assertions.assertEquals(ProjectStatus.DRAFT.toString(), dto.getProjectStatus().toString());
    }

    @Test
    public void createProjectByNullDto() throws Exception {
        try {
            service.createNewProject(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Null project dto!", ex.getMessage());
        }
    }

    @Test
    public void createProjectWithEmptyCodeName() {
        try {
            Project project = getSomeProject();
            project.setProjectCodeName("");
            service.createNewProject(getCreateDto(project));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty project code name!", ex.getMessage());
        }
    }


    @Test
    public void createProjectWithEmptyName() {
        try {
            Project project = getSomeProject();
            project.setProjectName("");
            service.createNewProject(getCreateDto(project));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty project name!", ex.getMessage());
        }
    }

    @Test
    public void createProjectWithNotUniqueCodeName() {
        try {
            Project project = getSomeProject();
            service.createNewProject(getCreateDto(project));
            Project project2 = getSomeProject();
            project2.setProjectCodeName(project.getProjectCodeName());
        } catch (Exception ex) {
            Assertions.assertEquals("Not unique project code name!", ex.getMessage());
        }
    }

    @Test
    public void updateProject() throws Exception {
        Project project = getSomeProject();
        service.createNewProject(getCreateDto(project));
        project.setDescription(UUID.randomUUID().toString());

        ProjectCardDto dto = service.changeProject(getUpdateDto(project));
        Assertions.assertEquals(project.getProjectCodeName(), dto.getProjectCodeName());
        Assertions.assertEquals(project.getProjectName(), dto.getProjectName());
        Assertions.assertEquals(project.getDescription(), dto.getDescription());
        Assertions.assertEquals(project.getProjectStatus().toString(), dto.getProjectStatus().toString());
    }


    @Test
    public void updateProjectByNullDto() {
        try {
            service.createNewProject(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Null project dto!", ex.getMessage());
        }
    }

    @Test
    public void updateProjectWithEmptyCodeName() {
        try {
            Project project = getSomeProject();
            service.createNewProject(getCreateDto(project));
            project.setProjectCodeName("");
            ProjectCardDto dto = service.changeProject(getUpdateDto(project));
        } catch (Exception ex) {
            Assertions.assertEquals("Project was not found!", ex.getMessage());
        }
    }

    @Test
    public void updateProjectWithEmptyName() {
        try {
            Project project = getSomeProject();
            service.createNewProject(getCreateDto(project));
            project.setProjectName("");
            ProjectCardDto dto = service.changeProject(getUpdateDto(project));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty project name!", ex.getMessage());
        }
    }

    @Test
    public void changeProjectToAvailableStatus() throws Exception {
        Project project = getSomeProject();
        service.createNewProject(getCreateDto(project));
        ProjectCardDto dto = service.changeProjectStatus(new ChangeProjectStatusDto(
                project.getProjectCodeName(), ProjectStatus.DEVELOPING.toString()
        ));
        Assertions.assertEquals(ProjectStatus.DEVELOPING.toString(), dto.getProjectStatus().toString());
    }

    @Test
    public void changeProjectToNotAvailableStatus() {
        try {
            Project project = getSomeProject();
            service.createNewProject(getCreateDto(project));
            ProjectCardDto dto = service.changeProjectStatus(new ChangeProjectStatusDto(
                    project.getProjectCodeName(), ProjectStatus.DONE.toString()
            ));
            Assertions.assertEquals(project.getProjectStatus().toString(), dto.getProjectStatus());
        } catch (Exception ex) {
            Assertions.assertEquals("Not available status!", ex.getMessage());
        }
    }


    @Test
    public void searchProject() throws Exception {
        Project project = getSomeProject();
        ProjectCardDto card = service.createNewProject(getCreateDto(project));
        List<ProjectCardDto> dto = service.searchProject(new SearchProjectDto(project.getProjectCodeName(), Arrays.asList(project.getProjectStatus())));
        Assertions.assertEquals(project.getProjectCodeName(), dto.get(0).getProjectCodeName());
        Assertions.assertEquals(project.getProjectName(), dto.get(0).getProjectName());
        Assertions.assertEquals(project.getDescription(), dto.get(0).getDescription());
        Assertions.assertEquals(project.getProjectStatus().toString(), dto.get(0).getProjectStatus().toString());
    }


    private CreateProjectDto getCreateDto(Project project) {
        return new CreateProjectDto(
                project.getProjectCodeName(),
                project.getProjectName(),
                project.getDescription()
        );
    }

    private UpdateProjectDto getUpdateDto(Project project) {
        return new UpdateProjectDto(
                project.getProjectCodeName(),
                project.getProjectName(),
                project.getDescription()
        );
    }

    private ChangeProjectStatusDto getStatusDto(Project project) {
        return new ChangeProjectStatusDto(
                project.getProjectCodeName(),
                project.getProjectStatus().toString()
        );
    }


}
