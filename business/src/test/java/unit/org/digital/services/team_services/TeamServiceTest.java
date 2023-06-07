package unit.org.digital.services.team_services;


import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_model.Project;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.team_member_services.MemberService;
import org.digital.services.team_services.TeamService;
import org.digital.team_dao.TeamRepository;
import org.digital.team_dto.AddMemberDto;
import org.digital.team_dto.GetAllMembersDto;
import org.digital.team_dto.RemoveMemberDto;
import org.digital.team_member_dao.TeamMemberRepository;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith({MockitoExtension.class})
public class TeamServiceTest {
    @Mock
    TeamRepository teamRepository;

    @Mock
    MemberService memberService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    ProjectRepository projectRepository;
    @Mock

    TeamMemberRepository teamMemberRepository;

    @InjectMocks
    TeamService teamService;


    @Test
    public void addMember() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        Team team = new Team();
        team.setProject(project);
        team.setMembers(null);
        team.setMembers(new ArrayList<TeamMember>());
        Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
        Mockito.when(teamRepository.findByProject(project)).thenReturn(Optional.ofNullable(team));
        Mockito.when(employeeRepository.findById(member.getMember().getAccountId())).thenReturn(Optional.ofNullable(member.getMember()));
        Mockito.when(memberService.getMemberByEmployeeAndRole(member.getMember(),member.getRole(),team))
                .thenReturn(member);
        Mockito.when(teamMemberRepository.findByMemberAndTeam(member.getMember(),team)).thenReturn(Optional.ofNullable(null));
        MemberCardDto dto = teamService.addMemberToTeam(new AddMemberDto(
                project.getProjectCodeName(),
                member.getMember().getAccountId(),
                member.getRole().toString()
        ));

        Assertions.assertEquals(member.getMember().getName(),dto.getEmployee().getName());
        Assertions.assertEquals(member.getRole().toString(),dto.getRole().toString());
    }

    @Test
    public void addFalseMember(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<TeamMember>());
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
            Mockito.when(teamRepository.findByProject(project)).thenReturn(Optional.of(team));
            Mockito.when(employeeRepository.findById(member.getMember().getAccountId())).thenReturn(Optional.ofNullable(null));
            MemberCardDto dto = teamService.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    member.getMember().getAccountId(),
                    member.getRole().toString()
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
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<TeamMember>());
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.ofNullable(null));
            MemberCardDto dto = teamService.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    member.getMember().getAccountId(),
                    member.getRole().toString()
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
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<TeamMember>());
            team.getMembers().add(member);
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
            Mockito.when(teamRepository.findByProject(project)).thenReturn(Optional.of(team));
            Mockito.when(employeeRepository.findById(member.getMember().getAccountId())).thenReturn(Optional.ofNullable(member.getMember()));
            Mockito.when(teamMemberRepository.findByMemberAndTeam(member.getMember(),team)).thenReturn(Optional.of(member));
            MemberCardDto dto = teamService.addMemberToTeam(new AddMemberDto(
                    project.getProjectCodeName(),
                    member.getMember().getAccountId(),
                    member.getRole().toString()
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Employee already in team!",ex.getMessage());
        }
    }

    @Test
    public void removeMember() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        Team team = new Team();
        team.setProject(project);
        team.setMembers(new ArrayList<>());
        team.getMembers().add(member);
        Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
        Mockito.when(teamRepository.findByProject(project))
                .thenReturn(Optional.of(team));
        Mockito.when(teamRepository.save(team)).thenReturn(team);

        MemberCardDto dto = teamService.removeMemberFromTeam(new RemoveMemberDto(
                project.getProjectCodeName(),
                member.getMember().getAccountId()
        ));

        Assertions.assertEquals(member.getMember().getName(),dto.getEmployee().getName());
        Assertions.assertEquals(member.getMember().getEmployeeStatus().toString(),dto.getEmployee().getStatus().toString());
    }

    @Test
    public void removeFalseMember(){
        try{
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<>());
            team.getMembers().add(member);
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
            Mockito.when(teamRepository.findByProject(project))
                    .thenReturn(Optional.of(team));

            MemberCardDto dto = teamService.removeMemberFromTeam(new RemoveMemberDto(
                    project.getProjectCodeName(),
                    member.getMember().getAccountId() + 1
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
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<>());
            team.getMembers().add(member);
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
            Mockito.when(teamRepository.findByProject(project))
                    .thenReturn(Optional.ofNullable(null));

            MemberCardDto dto = teamService.removeMemberFromTeam(new RemoveMemberDto(
                    project.getProjectCodeName(),
                    member.getMember().getAccountId()
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Project was not found!",ex.getMessage());
        }
    }

    @Test
    public void getAllMembers() throws Exception {
        TeamMember member = getSomeMember();
        Project project = getSomeProject();
        Team team = new Team();
        team.setProject(project);
        team.setMembers(new ArrayList<>());
        team.getMembers().add(member);
        Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
        Mockito.when(teamRepository.findByProject(project))
                .thenReturn(Optional.of(team));
        List<MemberCardDto> dto = teamService.getAllMembers(new GetAllMembersDto(
                project.getProjectCodeName()
        ));
        Assertions.assertEquals(member.getMember().getName(),dto.get(0).getEmployee().getName());
        Assertions.assertEquals(member.getMember().getSurname(),dto.get(0).getEmployee().getSurname());
    }


    @Test
    public void getAllMembersFromFalseProject(){
        try {
            TeamMember member = getSomeMember();
            Project project = getSomeProject();
            Team team = new Team();
            team.setProject(project);
            team.setMembers(new ArrayList<>());
            team.getMembers().add(member);
            Mockito.when(projectRepository.findById(project.getProjectCodeName())).thenReturn(Optional.of(project));
            Mockito.when(teamRepository.findByProject(project))
                    .thenReturn(Optional.ofNullable(null));
            List<MemberCardDto> dto = teamService.getAllMembers(new GetAllMembersDto(
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
                EmployeeProjectRole.SUPERVISOR,
                null
        );
    }

    private Project getSomeProject(){
        return new Project(
                "CodeName",
                "Name",
                "Decs",
                ProjectStatus.DEVELOPING
        );
    }
}
