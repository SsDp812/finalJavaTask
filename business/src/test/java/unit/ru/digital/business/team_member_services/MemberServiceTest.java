package unit.ru.digital.business.team_member_services;


import ru.digital.models.employee_model.Employee;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.commons.exceptions.member_exception.NullMemberEmployeeException;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.business.team_member_services.Impls.MemberServiceImpl;
import ru.digital.dao.team_member_dao.TeamMemberRepository;
import ru.digital.models.team_member_model.TeamMember;
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
    private MemberServiceImpl memberService;

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
