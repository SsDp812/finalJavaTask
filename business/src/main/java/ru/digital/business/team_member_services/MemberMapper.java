package ru.digital.business.team_member_services;

import ru.digital.models.employee_model.Employee;
import ru.digital.dto.member_dto.response_member_dto.MemberCardDto;
import ru.digital.commons.roles.EmployeeProjectRole;
import ru.digital.business.employee_services.EmployeeMapper;

public class MemberMapper {
    //Mapper for mapping member to MemberCardDto
    public static MemberCardDto getMemberCard(Employee employee, EmployeeProjectRole role) {
        MemberCardDto memberCardDto = new MemberCardDto();
        memberCardDto.setEmployee(EmployeeMapper.getEmployeeDtoCard(employee));
        memberCardDto.setRole(role);
        return memberCardDto;
    }
}
