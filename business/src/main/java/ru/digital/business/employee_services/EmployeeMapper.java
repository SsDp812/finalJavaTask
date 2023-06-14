package ru.digital.business.employee_services;

import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.models.employee_model.Employee;

public class EmployeeMapper {

    //Mapper for mapping employee to EmployeeCardDto
    public static EmployeeCardDto getEmployeeDtoCard(Employee employee) {
        EmployeeCardDto dtoCard = new EmployeeCardDto();
        dtoCard.setId(employee.getAccountId());
        dtoCard.setSurname(employee.getSurname());
        dtoCard.setName(employee.getName());
        dtoCard.setMiddleName(employee.getMiddleName());
        dtoCard.setJobTitle(employee.getJobTitle());
        dtoCard.setEmail(employee.getEmail());
        dtoCard.setStatus(employee.getEmployeeStatus());
        return dtoCard;
    }
}
