package org.digital.services.employee_services;



import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dao.specifications.EmployeeSpecifications;
import org.digital.employee_dto.request_employee_dto.*;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.exceptions.employee_exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepository repository;
    private BCryptPasswordEncoder passwordEncoder;


    @Autowired
    public EmployeeService(EmployeeRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeCardDto createNewEmployee(CreateEmployeeDto dto) throws Exception {
        if(dto == null){
            throw new NullEmployeeDtoException();
        }
       else if(Objects.equals(dto.getSurname(),"")) {
            throw new EmptyEmployeeSurnameException();
       }
       else if(Objects.equals(dto.getName(),"")){
            throw new EmptyEmployeeNameException();
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
       repository.save(employee);
       return EmployeeMapper.getEmployeeDtoCard(employee);
    }



    public EmployeeCardDto changeEmployeeInfo(UpdateEmployeeDto dto) throws Exception {
        if(dto == null){
            throw new NullEmployeeDtoException();
        }
       Optional<Employee> employeeOptional = repository.findById(dto.getAccountId());

       if(employeeOptional.isPresent()){
           Employee employee = employeeOptional.get();
           if(employee.getEmployeeStatus() == EmployeeStatus.DELETED){
               throw new EmployeeAlreadyDeletedException();
           }else{
               if(Objects.equals(dto.getSurname(),"")){
                   throw new EmptyEmployeeSurnameException();
               }else if(Objects.equals(dto.getName(),"")){
                   throw new EmptyEmployeeNameException();
               }
               employee.setSurname(dto.getSurname());
               employee.setName(dto.getName());
               employee.setMiddleName(dto.getMiddleName());
               employee.setJobTitle(dto.getJobTitle());
               employee.setEmail(dto.getEmail());
               employee.setLogin(dto.getLogin());
               employee.setPassword(passwordEncoder.encode(dto.getPassword()));
               repository.save(employee);
               return EmployeeMapper.getEmployeeDtoCard(employee);
           }
       }else{
           throw new EmployeeNotFoundException();
       }
    }


    public EmployeeCardDto deleteEmployee(DeleteEmployeeDto dto) throws Exception {
        if(dto == null){
            throw new NullEmployeeDtoException();
        }
        Optional<Employee> optionalEmployee = repository.findById(dto.getAccountId());
        if(optionalEmployee.isPresent()){
            if(optionalEmployee.get().getEmployeeStatus() == EmployeeStatus.DELETED){
                throw new EmployeeAlreadyDeletedException();
            }
            Employee employee = optionalEmployee.get();
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            repository.save(employee);
            return EmployeeMapper.getEmployeeDtoCard(employee);
        }else{
            throw new Exception("Error employee id, employee not found!");
        }
    }


    public EmployeeCardDto getEmployeeCardById(GetByIdEmployeeDto dtoId) throws Exception {
        if(dtoId == null){
            throw new NullEmployeeDtoException();
        }
        EmployeeCardDto dtoCard = new EmployeeCardDto();
        Optional<Employee> optionalEmployee = repository.findById(dtoId.getAccountId());
        if(optionalEmployee.isPresent()){
           return EmployeeMapper.getEmployeeDtoCard(optionalEmployee.get());
        }else{
            throw new EmployeeNotFoundException();
        }
    }



    public EmployeeCardDto getEmployeeByAccount(GetEmployeeByLoginAndPassword accountDto) throws Exception {
        if(accountDto == null){
            throw new NullEmployeeDtoException();
        }
        Optional<Employee> optionalEmployee = repository.findByLoginAndPassword(accountDto.getLogin(),accountDto.getPassword());
        if(optionalEmployee.isPresent()){
            return EmployeeMapper.getEmployeeDtoCard(optionalEmployee.get());
        }else{
            throw new EmployeeNotFoundException();
        }
    }

    public List<EmployeeCardDto> searchEmployee(SearchEmployeeDto searchDto) throws Exception {
        if(searchDto == null){
            throw new NullEmployeeDtoException();
        }
        List<Employee> employees = repository.findAll(EmployeeSpecifications.
               searchByFilterAndStatuses(searchDto.getSearchFilter(), EmployeeStatus.ACTIVE));
        List<EmployeeCardDto> list = new ArrayList<>();
        for(var employee : employees){
            list.add(EmployeeMapper.getEmployeeDtoCard(employee));
        }
        return list;
    }
}
