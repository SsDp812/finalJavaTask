package org.digital.team_member_model;

import lombok.Data;
import org.digital.employee_model.Employee;
import org.digital.roles.EmployeeProjectRole;

@Data
public class TeamMember {
    private Employee member;
    private EmployeeProjectRole role;
}
