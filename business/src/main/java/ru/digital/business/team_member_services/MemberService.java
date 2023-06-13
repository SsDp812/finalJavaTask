package ru.digital.business.team_member_services;

import ru.digital.models.employee_model.Employee;
import ru.digital.commons.exceptions.member_exception.NullMemberEmployeeException;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.models.team_member_model.TeamMember;
import ru.digital.models.team_model.Team;

public interface MemberService {
    public TeamMember getMemberByEmployeeAndRole(Employee employee, EmployeeProjectRole role, Team team) throws NullMemberEmployeeException;
}
