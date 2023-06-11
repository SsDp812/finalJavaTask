package services.team_member_services;

import org.digital.Main;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_model.Project;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.project_services.ProjectServiceImpl;
import org.digital.services.team_member_services.Impls.MemberServiceImpl;
import org.digital.services.team_services.Impls.TeamServiceImpl;
import org.digital.team_dao.TeamRepository;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import services.BaseTest;

import java.util.Optional;

@SpringBootTest(classes = Main.class)
public class MemberServiceIntegrationTest extends BaseTest {
    @Autowired
    private MemberServiceImpl service;

    @Autowired
    private TeamServiceImpl teamService;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private MemberServiceImpl memberService;
    @Test
    public void getMember() throws Exception {

        Project project = getSomeProject();
       projectService.createNewProject(new CreateProjectDto(
               project.getProjectCodeName(),
               project.getProjectName(),
               project.getDescription()
       ));
        TeamMember teamMember = new TeamMember();
        Optional<Team> team = teamRepository.findByProject(project);
        Employee employee = getSomeEmloyee();
        employee = employeeRepository.save(employee);
        teamMember.setMember(employee);
        teamMember.setRole(EmployeeProjectRole.ANALYST);
        teamMember.setTeam(team.get());
        TeamMember member= memberService.getMemberByEmployeeAndRole(employee,EmployeeProjectRole.ANALYST,team.get());
        Assertions.assertEquals(employee.getName(),member.getMember().getName());
        Assertions.assertEquals(EmployeeProjectRole.ANALYST.toString(),member.getRole().toString());
    }



}
