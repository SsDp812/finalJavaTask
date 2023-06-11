package org.digital.services.team_member_services;

import org.digital.employee_model.Employee;
import org.digital.exceptions.member_exception.NullMemberEmployeeException;
import org.digital.roles.EmployeeProjectRole;
import org.digital.team_member_model.TeamMember;
import org.digital.team_model.Team;

public interface MemberService {
    public TeamMember getMemberByEmployeeAndRole(Employee employee, EmployeeProjectRole role, Team team) throws NullMemberEmployeeException;
}
