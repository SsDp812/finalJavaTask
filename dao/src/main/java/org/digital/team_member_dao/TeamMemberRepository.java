package org.digital.team_member_dao;

import org.digital.employee_model.Employee;
import org.digital.roles.EmployeeProjectRole;
import org.digital.team_member_model.TeamMember;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember,Long> {
    Optional<TeamMember> findByMemberAndRole(Employee member, EmployeeProjectRole role);
}
