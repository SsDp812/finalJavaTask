package services.team_member_services;

import org.digital.Main;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_model.Project;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.project_services.ProjectService;
import org.digital.services.team_member_services.MemberService;
import org.digital.services.team_services.TeamService;
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
    private MemberService service;

    @Autowired
    private TeamService teamService;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MemberService memberService;
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


    private Project getSomeProject() {
        return new Project(
                "CodeName",
                "Name",
                "desc",
                ProjectStatus.DEVELOPING
        );
    }
    private Employee getSomeEmloyee(){
        Employee employee = new Employee(
                null,
                "Ivanov",
                "Ivan",
                "Ivanovich",
                "IT",
                "login",
                "password",
                "email",
                EmployeeStatus.ACTIVE
        );
        return employee;
    }
}
