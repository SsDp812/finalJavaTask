package org.digital.services.employee_services;

import org.digital.employee_dto.request_employee_dto.*;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;

import java.util.List;

public interface EmployeeService {
    public EmployeeCardDto createNewEmployee(CreateEmployeeDto dto) throws Exception;

    public EmployeeCardDto changeEmployeeInfo(UpdateEmployeeDto dto) throws Exception;

    public EmployeeCardDto deleteEmployee(DeleteEmployeeDto dto) throws Exception;

    public EmployeeCardDto getEmployeeCardById(GetByIdEmployeeDto dtoId) throws Exception;

    public EmployeeCardDto getEmployeeByAccount(GetEmployeeByLoginAndPassword accountDto) throws Exception;

    public List<EmployeeCardDto> searchEmployee(SearchEmployeeDto searchDto) throws Exception;
}
