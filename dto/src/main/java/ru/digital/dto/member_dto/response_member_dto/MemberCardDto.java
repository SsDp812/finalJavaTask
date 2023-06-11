package ru.digital.dto.member_dto.response_member_dto;

import lombok.Data;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.commons.roles.EmployeeProjectRole;

@Data
public class MemberCardDto {
    private EmployeeCardDto employee;
    private EmployeeProjectRole role;
}
