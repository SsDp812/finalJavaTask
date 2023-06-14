package ru.digital.controllers.employee_сontrollers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.digital.business.employee_services.EmployeeService;
import ru.digital.dto.employee_dto.request_employee_dto.*;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;

import java.util.List;

@RestController
@RequestMapping("/employee")
@Tag(name = "EmployeeController", description = "Controller for employees")
public class EmployeeController {
    private EmployeeService service;

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @Operation(summary = "Creating new employee")
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto createNew(@RequestBody @Valid CreateEmployeeDto dto) {
        return service.createNewEmployee(dto);
    }


    @Operation(summary = "CUpdating employee info")
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeCardDto updateEmployee(@RequestBody @Valid UpdateEmployeeDto dto) {
        return service.changeEmployeeInfo(dto);
    }

    @Operation(summary = "Deleting employee")
    @PostMapping(value = "/delete")
    public EmployeeCardDto deleteEmployee(@RequestParam @Positive Long id) {
        return service.deleteEmployee(new DeleteEmployeeDto(id));
    }

    @Operation(summary = "Searching employee by filter")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EmployeeCardDto> searchEmployee(@RequestBody String filter) {
        return service.searchEmployee(new SearchEmployeeDto(filter));
    }

    @Operation(summary = "Get employee by id")
    @PostMapping(value = "/getById")
    public EmployeeCardDto getEmployeeById(@RequestBody @Positive Long id) {
        return service.getEmployeeCardById(id);
    }

    @Operation(summary = "Get employee by login and password")
    @GetMapping(value = "/getByAccount")
    public EmployeeCardDto getEmployeeByAccount(@RequestParam("login") String login, @RequestParam("password") String password) {
        return service.getEmployeeByAccount(new GetEmployeeByLoginAndPassword(login, password));
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        return ex.getMessage();
    }

}
