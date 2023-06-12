package ru.digital.business.team_member_services.Impls;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.business.team_member_services.MemberService;
import ru.digital.commons.exceptions.member_exception.NullMemberEmployeeException;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.dao.team_member_dao.TeamMemberRepository;
import ru.digital.models.employee_model.Employee;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;

import java.util.Optional;


@Service
@Transactional
@Slf4j
public class MemberServiceImpl implements MemberService {
    private TeamMemberRepository repository;

    @Autowired
    public MemberServiceImpl(TeamMemberRepository repository) {
        this.repository = repository;
    }

    public TeamMember getMemberByEmployeeAndRole(Employee employee, EmployeeProjectRole role, Team team) throws NullMemberEmployeeException {
        if (employee == null) {
            throw new NullMemberEmployeeException();
        }
        Optional<TeamMember> optionalTeamMember = repository.findByMemberAndRoleAndTeam(employee, role, team);
        if (optionalTeamMember.isPresent()) {
            return optionalTeamMember.get();
        } else {
            TeamMember teamMember = new TeamMember();
            teamMember.setTeam(team);
            teamMember.setMember(employee);
            teamMember.setRole(role);
            log.info("New role: " + role.toString() + " to employee with account id: " + employee.getAccountId().toString());
            repository.save(teamMember);
            return teamMember;
        }
    }
}
