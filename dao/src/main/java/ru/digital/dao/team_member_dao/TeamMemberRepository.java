package ru.digital.dao.team_member_dao;

import ru.digital.models.employee_model.Employee;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
    Optional<TeamMember> findByMemberAndRoleAndTeam(Employee member, EmployeeProjectRole role, Team team);
    Optional<TeamMember> findByMemberAndTeam(Employee member, Team team);
}
