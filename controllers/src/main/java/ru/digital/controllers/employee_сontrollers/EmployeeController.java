package ru.digital.controllers.employee_сontrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import ru.digital.dto.employee_dto.request_employee_dto.*;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.business.employee_services.EmployeeService;
import ru.digital.business.employee_services.Impls.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "EmployeeController",description = "Controller for employees")
public class EmployeeController {
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeServiceImpl service) {
        this.service = service;
    }

    @Operation(summary = "Creating new employee")
    @PostMapping(value = "/new",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto createNew(@RequestBody CreateEmployeeDto dto) throws Exception {
        return service.createNewEmployee(dto);
    }


    @Operation(summary = "CUpdating employee info")
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto updateEmployee(@RequestBody UpdateEmployeeDto dto) throws Exception {
        return service.changeEmployeeInfo(dto);
    }

    @Operation(summary = "Deleting employee")
    @PostMapping(value = "/delete", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto deleteEmployee(@RequestBody DeleteEmployeeDto dto) throws Exception {
        return service.deleteEmployee(dto);
    }

    @Operation(summary = "Searching employee by filter")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeCardDto> searchEmployee(@RequestBody SearchEmployeeDto dto) throws Exception {
        return service.searchEmployee(dto);
    }

    @Operation(summary = "Get employee by id")
    @PostMapping(value = "/getById", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto getEmployeeById(@RequestBody GetByIdEmployeeDto dto) throws Exception {
        return service.getEmployeeCardById(dto);
    }

    @Operation(summary = "Get employee by login and password")
    @GetMapping(value = "/getByAccount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto getEmployeeByAccount(@RequestBody GetEmployeeByLoginAndPassword dto) throws Exception {
        return service.getEmployeeByAccount(dto);
    }
}
