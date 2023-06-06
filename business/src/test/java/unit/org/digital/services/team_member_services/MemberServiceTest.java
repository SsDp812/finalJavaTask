package unit.org.digital.services.team_member_services;


import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.exceptions.member_exception.NullMemberEmployeeException;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.team_member_services.MemberService;
import org.digital.team_member_dao.TeamMemberRepository;
import org.digital.team_member_model.TeamMember;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith({MockitoExtension.class})
public class MemberServiceTest  {
    @Mock
    private TeamMemberRepository teamMemberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    public void getMember() throws NullMemberEmployeeException {
        TeamMember teamMember = new TeamMember();
        teamMember.setMember(getSomeEmloyee());
        teamMember.setRole(EmployeeProjectRole.ANALYST);

        Mockito.when(teamMemberRepository.findByMemberAndRoleAndTeam(teamMember.getMember(),teamMember.getRole(),null))
                .thenReturn(Optional.of(teamMember));
        TeamMember newTeamMember = memberService.getMemberByEmployeeAndRole(teamMember.getMember(),teamMember.getRole(),null);
        Assertions.assertEquals(teamMember.getMember().getName(),newTeamMember.getMember().getName());
        Assertions.assertEquals(teamMember.getRole().toString(),newTeamMember.getRole().toString());
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
