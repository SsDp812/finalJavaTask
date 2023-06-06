package org.digital.services.team_member_services;


import lombok.extern.slf4j.Slf4j;
import org.digital.employee_model.Employee;
import org.digital.exceptions.member_exception.NullMemberEmployeeException;
import org.digital.roles.EmployeeProjectRole;
import org.digital.team_member_dao.TeamMemberRepository;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Transactional
@Slf4j
public class MemberService {
    private TeamMemberRepository repository;

    @Autowired
    public MemberService(TeamMemberRepository repository) {
        this.repository = repository;
    }

    public TeamMember getMemberByEmployeeAndRole(Employee employee, EmployeeProjectRole role, Team team) throws NullMemberEmployeeException {
        if (employee == null) {
            throw new NullMemberEmployeeException();
        }
        Optional<TeamMember> optionalTeamMember = repository.findByMemberAndRoleAndTeam(employee, role,team);
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
