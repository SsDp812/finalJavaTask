package services.team_services;


import org.checkerframework.checker.units.qual.A;
import org.digital.Main;
import org.digital.employee_dto.request_employee_dto.CreateEmployeeDto;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.project_dto.request_project_dto.CreateProjectDto;
import org.digital.project_model.Project;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.employee_services.EmployeeService;
import org.digital.services.project_services.ProjectService;
import org.digital.services.team_member_services.MemberService;
import org.digital.services.team_services.TeamService;
import org.digital.team_dao.TeamRepository;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import services.BaseTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest(classes = Main.class)
public class TeamServiceIntegrationTest extends BaseTest {

    @Autowired
    private TeamService service;
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private ProjectService projectService;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void addMember() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        projectService.createNewProject(new CreateProjectDto(
                project.getProjectCodeName(),
                project.getProjectName(),
                project.getDescription()
                )
        );
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                project.getProjectCodeName(),
                employee.getAccountId(),
                EmployeeProjectRole.ANALYST.toString()
        ));

        Assertions.assertEquals(employeeCardDto.getName(),dto.getEmployee().getName());
        Assertions.assertEquals(EmployeeProjectRole.ANALYST.toString(),dto.getRole().toString());
    }

    @Test
    public void addFalseMember(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId() + 100,
                    EmployeeProjectRole.ANALYST.toString()
            ));

        }catch (Exception ex){
            Assertions.assertEquals("Employee was not found!",ex.getMessage());
        }
    }

    @Test
    public void addMemberToFalseProject(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName() + "!",
                    employee.getAccountId(),
                    EmployeeProjectRole.ANALYST.toString()
            ));

        } catch (Exception ex) {
            Assertions.assertEquals("Project was not found!",ex.getMessage());
        }
    }

    @Test
    public void addMemberWhoAlreadyInTeam(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId(),
                    EmployeeProjectRole.ANALYST.toString()
            ));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto2 = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId(),
                    EmployeeProjectRole.SUPERVISOR.toString()
            ));

        }catch (Exception ex){
            Assertions.assertEquals("Employee already in team!",ex.getMessage());
        }
    }

    @Test
    public void removeMember() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        projectService.createNewProject(new CreateProjectDto(
                        project.getProjectCodeName(),
                        project.getProjectName(),
                        project.getDescription()
                )
        );
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                project.getProjectCodeName(),
                employee.getAccountId(),
                EmployeeProjectRole.ANALYST.toString()
        ));
        MemberCardDto removedMember = service.removeMemberFromTeam(new RemoveMemberDto(
                project.getProjectCodeName(),
                employee.getAccountId()
        ));

        Assertions.assertEquals(employeeCardDto.getName(),removedMember.getEmployee().getName());
        Assertions.assertEquals(employee.getEmployeeStatus().toString(),removedMember.getEmployee().getStatus().toString());
    }

    @Test
    public void removeFalseMember(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId(),
                    EmployeeProjectRole.ANALYST.toString()
            ));
            MemberCardDto removedMember = service.removeMemberFromTeam(new RemoveMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId() + 1000
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Employee was not found!",ex.getMessage());
        }
    }

    @Test
    public void removeMemberFromFalseProject(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    employee.getAccountId(),
                    EmployeeProjectRole.ANALYST.toString()
            ));
            MemberCardDto removedMember = service.removeMemberFromTeam(new RemoveMemberDto(
                    project.getProjectCodeName() + "!",
                    employee.getAccountId()
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Project was not found!",ex.getMessage());
        }
    }

    @Test
    public void getAllMembers() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        projectService.createNewProject(new CreateProjectDto(
                        project.getProjectCodeName(),
                        project.getProjectName(),
                        project.getDescription()
                )
        );
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                project.getProjectCodeName(),
                employee.getAccountId(),
                EmployeeProjectRole.ANALYST.toString()
        ));

        List<MemberCardDto> members = service.getAllMembers(new GetAllMembersDto(
                project.getProjectCodeName()
        ));
        Assertions.assertEquals(member.getMember().getName(), members.get(0).getEmployee().getName());
        Assertions.assertEquals(member.getMember().getSurname(),members.get(0).getEmployee().getSurname());
    }


    @Test
    public void getAllMembersFromFalseProject(){
        try {
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            projectService.createNewProject(new CreateProjectDto(
                            project.getProjectCodeName(),
                            project.getProjectName(),
                            project.getDescription()
                    )
            );
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            MemberCardDto dto = service.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName() + "!",
                    employee.getAccountId(),
                    EmployeeProjectRole.ANALYST.toString()
            ));

            List<MemberCardDto> members = service.getAllMembers(new GetAllMembersDto(
                    project.getProjectCodeName()
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Project was not found!",ex.getMessage());
        }
    }

    private TeamMember getSomeMember(){
        return new TeamMember(
                null,
                new Employee(
                        Long.valueOf(1),
                        "Ivanov",
                        "Ivan",
                        "Ivanovich",
                        "IT",
                        "login",
                        "password",
                        "email",
                        EmployeeStatus.ACTIVE
                ),
                EmployeeProjectRole.ANALYST,
                null
        );
    }



    private CreateEmployeeDto getCreateEmployeeDto(Employee employee) {
        return new CreateEmployeeDto(
                employee.getSurname(),
                employee.getName(),
                employee.getMiddleName(),
                employee.getJobTitle(),
                employee.getEmail(),
                employee.getLogin(),
                employee.getPassword()
        );
    }

}
