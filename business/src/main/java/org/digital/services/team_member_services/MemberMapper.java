package org.digital.services.team_member_services;

import org.digital.employee_model.Employee;
import org.digital.member_dto.response_member_dto.MemberCardDto;
import org.digital.roles.EmployeeProjectRole;
import org.digital.services.employee_services.EmployeeMapper;

public class MemberMapper {
    //Mapper for mapping member to MemberCardDto
    public static MemberCardDto getMemberCard(Employee employee, EmployeeProjectRole role) {
        MemberCardDto memberCardDto = new MemberCardDto();
        memberCardDto.setEmployee(EmployeeMapper.getEmployeeDtoCard(employee));
        memberCardDto.setRole(role);
        return memberCardDto;
    }
}
