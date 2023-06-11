package org.digital.services.employee_services.Impls;


import lombok.extern.slf4j.Slf4j;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dao.specifications.EmployeeSpecifications;
import org.digital.employee_dto.request_employee_dto.*;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.exceptions.employee_exceptions.*;
import org.digital.services.employee_services.EmployeeMapper;
import org.digital.services.employee_services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private EmployeeRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeCardDto createNewEmployee(CreateEmployeeDto dto) throws Exception {
        if (dto == null) {
            throw new NullEmployeeDtoException();
        } else if (Objects.equals(dto.getSurname(), "") || dto.getSurname() == null) {
            throw new EmptyEmployeeSurnameException();
        } else if (Objects.equals(dto.getName(), "") || dto.getName() == null) {
            throw new EmptyEmployeeNameException();
        } else if (Objects.equals(dto.getLogin(), "") || dto.getLogin() == null) {
            throw new EmployeeEmptyLoginException();
        } else if (Objects.equals(dto.getPassword(), "") || dto.getPassword() == null) {
            throw new EmployeeEmptyPasswordException();
        }
        Optional<Employee> optionalEmployee = repository.findByLogin(dto.getLogin());
        if (optionalEmployee.isPresent()) {
            throw new NotUniqieLoginException();
        }

        Employee employee = new Employee();
        employee.setSurname(dto.getSurname());
        employee.setName(dto.getName());
        employee.setMiddleName(dto.getMiddleName());
        employee.setJobTitle(dto.getJobTitle());
        employee.setLogin(dto.getLogin());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setEmail(dto.getEmail());
        employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
        employee = repository.save(employee);
        log.info("Created new employee with id: " + employee.getAccountId().toString());
        return EmployeeMapper.getEmployeeDtoCard(employee);
    }


    public EmployeeCardDto changeEmployeeInfo(UpdateEmployeeDto dto) throws Exception {
        if (dto == null) {
            throw new NullEmployeeDtoException();
        }
        Optional<Employee> employeeOptional = repository.findById(dto.getAccountId());

        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            if (employee.getEmployeeStatus() == EmployeeStatus.DELETED) {
                throw new EmployeeAlreadyDeletedException();
            } else {
                if (Objects.equals(dto.getSurname(), "") || dto.getSurname() == null) {
                    throw new EmptyEmployeeSurnameException();
                } else if (Objects.equals(dto.getName(), "") || dto.getName() == null) {
                    throw new EmptyEmployeeNameException();
                } else if (Objects.equals(dto.getLogin(), "") || dto.getLogin() == null) {
                    throw new EmployeeEmptyLoginException();
                } else if (Objects.equals(dto.getPassword(), "") || dto.getPassword() == null) {
                    throw new EmployeeEmptyPasswordException();
                }
                if (!Objects.equals(employee.getLogin(), dto.getLogin())) {
                    Optional<Employee> optionalEmployee = repository.findByLogin(dto.getLogin());
                    if (optionalEmployee.isPresent()) {
                        throw new NotUniqieLoginException();
                    }
                }

                employee.setSurname(dto.getSurname());
                employee.setName(dto.getName());
                employee.setMiddleName(dto.getMiddleName());
                employee.setJobTitle(dto.getJobTitle());
                employee.setEmail(dto.getEmail());
                employee.setLogin(dto.getLogin());
                employee.setPassword(passwordEncoder.encode(dto.getPassword()));
                employee = repository.save(employee);
                log.info("Employee info with id: " + employee.getAccountId().toString() + "was changed!");
                return EmployeeMapper.getEmployeeDtoCard(employee);
            }
        } else {
            throw new EmployeeNotFoundException();
        }
    }


    public EmployeeCardDto deleteEmployee(DeleteEmployeeDto dto) throws Exception {
        if (dto == null) {
            throw new NullEmployeeDtoException();
        }
        Optional<Employee> optionalEmployee = repository.findById(dto.getAccountId());
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            if (employee.getEmployeeStatus() == EmployeeStatus.DELETED) {
                log.warn("Employee with id: " + employee.getAccountId().toString() + "was already deleted!");
                throw new EmployeeAlreadyDeletedException();
            }
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            employee = repository.save(employee);
            log.info("Employee with id: " + employee.getAccountId().toString() + "was deleted!");
            return EmployeeMapper.getEmployeeDtoCard(employee);
        } else {
            throw new EmployeeNotFoundException();
        }
    }


    public EmployeeCardDto getEmployeeCardById(GetByIdEmployeeDto dtoId) throws Exception {
        if (dtoId == null) {
            throw new NullEmployeeDtoException();
        }
        EmployeeCardDto dtoCard = new EmployeeCardDto();
        Optional<Employee> optionalEmployee = repository.findById(dtoId.getAccountId());
        if (optionalEmployee.isPresent()) {
            return EmployeeMapper.getEmployeeDtoCard(optionalEmployee.get());
        } else {
            throw new EmployeeNotFoundException();
        }
    }


    public EmployeeCardDto getEmployeeByAccount(GetEmployeeByLoginAndPassword accountDto) throws Exception {
        if (accountDto == null) {
            throw new NullEmployeeDtoException();
        }
        Optional<Employee> optionalEmployee = repository.findByLogin(accountDto.getLogin());
        if (optionalEmployee.isPresent() && passwordEncoder.matches(accountDto.getPassword(), optionalEmployee.get().getPassword())) {
            return EmployeeMapper.getEmployeeDtoCard(optionalEmployee.get());
        } else {
            throw new EmployeeNotFoundException();
        }
    }

    public List<EmployeeCardDto> searchEmployee(SearchEmployeeDto searchDto) throws Exception {
        if (searchDto == null) {
            throw new NullEmployeeDtoException();
        }
        List<Employee> employees = repository.findAll(EmployeeSpecifications.
                searchByFilterAndStatuses(searchDto.getSearchFilter(), EmployeeStatus.ACTIVE));
        List<EmployeeCardDto> list = new ArrayList<>();
        for (var employee : employees) {
            list.add(EmployeeMapper.getEmployeeDtoCard(employee));
        }
        return list;
    }
}
