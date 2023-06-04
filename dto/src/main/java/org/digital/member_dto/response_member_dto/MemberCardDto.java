package org.digital.member_dto.response_member_dto;

import lombok.Data;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.roles.EmployeeProjectRole;

@Data
public class MemberCardDto {
    private EmployeeCardDto employee;
    private EmployeeProjectRole role;
}
