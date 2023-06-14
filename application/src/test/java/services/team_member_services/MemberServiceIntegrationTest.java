package services.team_member_services;

import ru.digital.application.Main;
import ru.digital.dao.employee_dao.EmployeeRepository;
import ru.digital.models.employee_model.Employee;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.models.project_model.Project;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.business.project_services.Impls.ProjectServiceImpl;
import ru.digital.business.team_member_services.Impls.MemberServiceImpl;
import ru.digital.business.team_services.Impls.TeamServiceImpl;
import ru.digital.dao.team_dao.TeamRepository;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;
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
