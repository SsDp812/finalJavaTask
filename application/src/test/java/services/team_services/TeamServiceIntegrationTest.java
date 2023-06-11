package services.team_services;


import ru.digital.application.Main;
import ru.digital.dto.employee_dto.request_employee_dto.CreateEmployeeDto;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.models.employee_model.Employee;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.dto.member_dto.response_member_dto.MemberCardDto;
import ru.digital.dto.project_dto.request_project_dto.CreateProjectDto;
import ru.digital.models.project_model.Project;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.business.employee_services.Impls.EmployeeServiceImpl;
import ru.digital.business.project_services.ProjectServiceImpl;
import ru.digital.business.team_member_services.Impls.MemberServiceImpl;
import ru.digital.business.team_services.Impls.TeamServiceImpl;
import ru.digital.dao.team_dao.TeamRepository;
import ru.digital.dto.team_dto.AddMemberDto;
import ru.digital.dto.team_dto.GetAllMembersDto;
import ru.digital.dto.team_dto.RemoveMemberDto;
import ru.digital.models.team_member_model.TeamMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import services.BaseTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = Main.class)
public class TeamServiceIntegrationTest extends BaseTest {

    @Autowired
    private TeamServiceImpl service;
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private MemberServiceImpl memberService;

    @Autowired
    private ProjectServiceImpl projectService;
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
