package ru.digital.business.employee_services;

import ru.digital.dto.employee_dto.request_employee_dto.*;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;

import java.util.List;

public interface EmployeeService {
    public EmployeeCardDto createNewEmployee(CreateEmployeeDto dto);

    public EmployeeCardDto changeEmployeeInfo(UpdateEmployeeDto dto);

    public EmployeeCardDto deleteEmployee(DeleteEmployeeDto dto);

    public EmployeeCardDto getEmployeeCardById(Long id);

    public EmployeeCardDto getEmployeeByAccount(GetEmployeeByLoginAndPassword accountDto);

    public List<EmployeeCardDto> searchEmployee(SearchEmployeeDto searchDto);
}
