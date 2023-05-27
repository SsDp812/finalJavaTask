package org.digital.services.team_member_services;

import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import org.digital.employee_model.Employee;
import org.digital.exceptions.member_exception.NullMemberEmployeeException;
import org.digital.roles.EmployeeProjectRole;
import org.digital.team_member_dao.TeamMemberRepository;
import org.digital.team_member_model.TeamMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@Transactional
public class MemberService {
    private TeamMemberRepository repository;

    @Autowired
    public MemberService(TeamMemberRepository repository) {
        this.repository = repository;
    }

    public TeamMember getMemberByEmployeeAndRole(Employee employee, EmployeeProjectRole role) throws NullMemberEmployeeException {
        if(employee == null){
            throw new NullMemberEmployeeException();
        }
        Optional<TeamMember> optionalTeamMember = repository.findByMemberAndRole(employee,role);
        if(optionalTeamMember.isPresent()){
            return optionalTeamMember.get();
        }else{
            TeamMember teamMember = new TeamMember();
            teamMember.setMember(employee);
            teamMember.setRole(role);
            repository.save(teamMember);
            return teamMember;
        }
    }
}
