package unit.org.digital.services.project_services;


import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_dto.request_project_dto.SearchProjectDto;
import org.digital.project_dto.request_project_dto.UpdateProjectDto;
import org.digital.project_dto.response_project_dto.ProjectCardDto;
import org.digital.project_model.Project;
import org.digital.services.project_services.ProjectServiceImpl;
import org.digital.team_dao.TeamRepository;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private ProjectRepository projectRepository;
    @InjectMocks
    private ProjectServiceImpl projectService;


    @Test
    public void createProject() throws Exception {
        Project project = getSomeProject();
        Mockito.when(projectRepository.findById(project.getProjectCodeName()))
                .thenReturn(Optional.ofNullable(null));
        Mockito.when(projectRepository.save(project)).thenReturn(project);

        Team team = new Team();
        team.setProject(project);
        team.setMembers(new ArrayList<TeamMember>());
        Mockito.when(teamRepository.save(team)).thenReturn(team);
        ProjectCardDto dto = projectService.createNewProject(getCreateDto(project));
        Assertions.assertEquals(project.getProjectCodeName(),dto.getProjectCodeName());
        Assertions.assertEquals(project.getProjectName(),dto.getProjectName());
        Assertions.assertEquals(project.getDescription(),dto.getDescription());
        Assertions.assertEquals(ProjectStatus.DRAFT.toString(),dto.getProjectStatus().toString());
    }

    @Test
    public void createProjectByNullDto() throws Exception {
        try{
            projectService.createNewProject(null);
        }catch (Exception ex){
            Assertions.assertEquals("Null project dto!",ex.getMessage());
        }
    }

    @Test
    public void createProjectWithEmptyCodeName(){
        try{
            Project project = getSomeProject();
            project.setProjectCodeName("");
            projectService.createNewProject(getCreateDto(project));
        }catch (Exception ex){
            Assertions.assertEquals("Empty project code name!",ex.getMessage());
        }
    }


    @Test
    public void createProjectWithEmptyName(){
        try{
            Project project = getSomeProject();
            project.setProjectName("");
            projectService.createNewProject(getCreateDto(project));
        }catch (Exception ex){
            Assertions.assertEquals("Empty project name!",ex.getMessage());
        }
    }

    @Test
    public void createProjectWithNotUniqueCodeName(){
        try{
            Project project = getSomeProject();
            Mockito.when(projectRepository.findById(project.getProjectCodeName()))
                    .thenReturn(Optional.of(project));
            projectService.createNewProject(getCreateDto(project));
        }catch (Exception ex){
            Assertions.assertEquals("Not unique project code name!",ex.getMessage());
        }
    }

    @Test
    public void updateProject() throws Exception {
        Project project = getSomeProject();

        Project projectUpdated = getSomeProject();
        projectUpdated.setProjectName("NEW");

        Mockito.when(projectRepository.findById(projectUpdated.getProjectCodeName()))
                .thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(projectUpdated)).thenReturn(projectUpdated);

        ProjectCardDto dto = projectService.changeProject(getUpdateDto(projectUpdated));
        Assertions.assertEquals(projectUpdated.getProjectCodeName(),dto.getProjectCodeName());
        Assertions.assertEquals(projectUpdated.getProjectName(),dto.getProjectName());
        Assertions.assertEquals(projectUpdated.getDescription(),dto.getDescription());
        Assertions.assertEquals(projectUpdated.getProjectStatus().toString(),dto.getProjectStatus().toString());
    }


    @Test
    public void updateProjectByNullDto(){
        try{
            projectService.createNewProject(null);
        }catch (Exception ex){
            Assertions.assertEquals("Null project dto!",ex.getMessage());
        }
    }

    @Test
    public void updateProjectWithEmptyCodeName(){
        try{
            Project project = getSomeProject();
            Project projectUpdated = getSomeProject();
            projectUpdated.setProjectCodeName("");
            Mockito.when(projectRepository.findById(projectUpdated.getProjectCodeName()))
                    .thenReturn(Optional.of(project));
            ProjectCardDto dto = projectService.changeProject(getUpdateDto(projectUpdated));
        }catch (Exception ex){
            Assertions.assertEquals("Empty project code name!",ex.getMessage());
        }
    }

    @Test
    public void updateProjectWithEmptyName(){
        try{
            Project project = getSomeProject();
            Project projectUpdated = getSomeProject();
            projectUpdated.setProjectName("");
            Mockito.when(projectRepository.findById(projectUpdated.getProjectCodeName()))
                    .thenReturn(Optional.of(project));
            ProjectCardDto dto = projectService.changeProject(getUpdateDto(projectUpdated));
        }catch (Exception ex){
            Assertions.assertEquals("Empty project name!",ex.getMessage());
        }
    }

    @Test
    public void changeProjectToAvailableStatus() throws Exception {
        Project project = getSomeProject();
        Project projectUpdated = getSomeProject();
        projectUpdated.setProjectStatus(ProjectStatus.DEVELOPING);
        Mockito.when(projectRepository.findById(project.getProjectCodeName()))
                .thenReturn(Optional.of(project));
        Mockito.when(projectRepository.save(projectUpdated)).thenReturn(projectUpdated);
        ProjectCardDto dto = projectService.changeProjectStatus(getStatusDto(projectUpdated));
        Assertions.assertEquals(projectUpdated.getProjectCodeName(),dto.getProjectCodeName());
        Assertions.assertEquals(projectUpdated.getProjectStatus().toString(),dto.getProjectStatus().toString());
    }

    @Test
    public void changeProjectToNotAvailableStatus(){
        try {
            Project project = getSomeProject();
            Project projectUpdated = getSomeProject();
            projectUpdated.setProjectStatus(ProjectStatus.TESTING);
            Mockito.when(projectRepository.findById(project.getProjectCodeName()))
                    .thenReturn(Optional.of(project));
            ProjectCardDto dto = projectService.changeProjectStatus(getStatusDto(projectUpdated));
        } catch (Exception ex) {
            Assertions.assertEquals("Not available status!",ex.getMessage());
        }
    }


    @Test
    public void searchProject() throws Exception {
        Project project = getSomeProject();
        Mockito.when(projectRepository.findAll((Specification<Project>) any())).thenReturn(Arrays.asList(project));
        List<ProjectCardDto> dto = projectService.searchProject(new SearchProjectDto("CodeName",Arrays.asList(ProjectStatus.DRAFT)));
        Assertions.assertEquals(project.getProjectCodeName(),dto.get(0).getProjectCodeName());
        Assertions.assertEquals(project.getProjectName(),dto.get(0).getProjectName());
        Assertions.assertEquals(project.getDescription(),dto.get(0).getDescription());
        Assertions.assertEquals(project.getProjectStatus().toString(),dto.get(0).getProjectStatus().toString());
    }



    private CreateProjectDto getCreateDto(Project project){
        return new CreateProjectDto(
                project.getProjectCodeName(),
                project.getProjectName(),
                project.getDescription()
        );
    }

    private UpdateProjectDto getUpdateDto(Project project){
        return new UpdateProjectDto(
                project.getProjectCodeName(),
                project.getProjectName(),
                project.getDescription()
        );
    }

    private ChangeProjectStatusDto getStatusDto(Project project){
        return new ChangeProjectStatusDto(
                project.getProjectCodeName(),
                project.getProjectStatus().toString()
        );
    }
    private Project getSomeProject(){
        return new Project(
          "CodeName",
          "Name",
          "Description",
                ProjectStatus.DRAFT
        );
    }

}
