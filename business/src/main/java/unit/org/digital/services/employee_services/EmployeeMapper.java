package unit.org.digital.services.employee_services;

import org.digital.employee_dto.request_employee_dto.CreateEmployeeDto;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;

public class EmployeeMapper {

    public static EmployeeCardDto getEmployeeDtoCard(Employee employee){
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
