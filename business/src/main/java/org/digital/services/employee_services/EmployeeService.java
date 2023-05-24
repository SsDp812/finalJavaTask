package org.digital.services.employee_services;


import jakarta.transaction.Transactional;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.request_employee_dto.*;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class EmployeeService {
    private EmployeeRepository repository;

    @Autowired
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }


    public void createNewEmployee(CreateEmployeeDto dto) throws Exception {
       Employee employee = new Employee();
       if(!Objects.equals(dto.getSurname(),"")){
           employee.setSurname(dto.getSurname());
       }else{
           throw new Exception("Empty surname!");
       }
        if(!Objects.equals(dto.getName(),"")){
            employee.setName(dto.getName());
        }else{
            throw new Exception("Empty name!");
        }
       employee.setMiddleName(dto.getMiddleName());
       employee.setJobTitle(dto.getJobTitle());
       employee.setLogin(dto.getLogin());
       employee.setPassword(dto.getPassword());
       employee.setEmail(dto.getEmail());
       employee.setEmployeeStatus(EmployeeStatus.ACTIVE);
       repository.save(employee);
    }



    public void changeEmployeeInfo(UpdateEmployeeDto dto) throws Exception {
       Optional<Employee> employeeOptional = repository.findById(dto.getAccountId());

       if(employeeOptional.isPresent()){
           Employee employee = employeeOptional.get();
           if(employee.getEmployeeStatus() == EmployeeStatus.DELETED){
               throw new Exception("Employee was deleted!");
           }
           else{
               if(!Objects.equals(dto.getSurname(),"")){
                   employee.setSurname(dto.getSurname());
               }else{
                   throw new Exception("Empty surname!");
               }
               if(!Objects.equals(dto.getName(),"")){
                   employee.setName(dto.getName());
               }else{
                   throw new Exception("Empty name!");
               }
               employee.setMiddleName(dto.getMiddleName());
               employee.setJobTitle(dto.getJobTitle());
               employee.setEmail(dto.getEmail());
               employee.setLogin(dto.getLogin());
               employee.setPassword(dto.getPassword());
               repository.save(employee);
           }
       }else{
           throw new Exception("Error employee id, employee not found!");
       }
    }


    public void deleteEmployee(DeleteEmployeeDto dto) throws Exception {
        Optional<Employee> optionalEmployee = repository.findById(dto.getAccountId());
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            repository.save(employee);
        }else{
            throw new Exception("Error employee id, employee not found!");
        }
    }


    public EmployeeCardDto getEmployeeCardById(GetByIdEmployeeDto dtoId) throws Exception {
        EmployeeCardDto dtoCard = new EmployeeCardDto();
        Optional<Employee> optionalEmployee = repository.findById(dtoId.getAccountId());
        if(optionalEmployee.isPresent()){
            Employee employee = optionalEmployee.get();
            dtoCard.setSurname(employee.getSurname());
            dtoCard.setSurname(employee.getSurname());
            dtoCard.setMiddleName(employee.getMiddleName());
            dtoCard.setJobTitle(employee.getJobTitle());
            dtoCard.setEmail(employee.getEmail());
            dtoCard.setStatus(employee.getEmployeeStatus().toString());
            return dtoCard;
        }else{
            throw new Exception("Error employee id, employee not found!");
        }
    }



    public EmployeeCardDto getEmployeeByAccount(GetEmployeeByLoginAndPassword accountDto) throws Exception {
        Optional<Employee> optionalEmployee = repository.findByLoginAndPassword(accountDto.getLogin(),accountDto.getPassword());
        if(optionalEmployee.isPresent()){
            EmployeeCardDto dtoCard = new EmployeeCardDto();
            Employee employee = optionalEmployee.get();
            dtoCard.setSurname(employee.getSurname());
            dtoCard.setSurname(employee.getSurname());
            dtoCard.setMiddleName(employee.getMiddleName());
            dtoCard.setJobTitle(employee.getJobTitle());
            dtoCard.setEmail(employee.getEmail());
            dtoCard.setStatus(employee.getEmployeeStatus().toString());
            return dtoCard;
        }else{
            throw new Exception("User not found!");
        }
    }

    public List<EmployeeCardDto> searchEmployee(SearchEmployeeDto searchDto) throws Exception {
        Optional<List<Employee>> optionalEmployees = repository.findBySurnameContainingOrNameContainingOrMiddleNameContainingOrJobTitleContainingOrLoginContainingOrEmailContainingAndEmployeeStatus(searchDto.getSearchFilter(),EmployeeStatus.ACTIVE);
        if(optionalEmployees.isPresent()){
            List<EmployeeCardDto> list = new ArrayList<>();
            List<Employee> employees = optionalEmployees.get();
            for(var employee : employees){
                EmployeeCardDto dtoCard = new EmployeeCardDto();
                dtoCard.setSurname(employee.getSurname());
                dtoCard.setSurname(employee.getSurname());
                dtoCard.setMiddleName(employee.getMiddleName());
                dtoCard.setJobTitle(employee.getJobTitle());
                dtoCard.setEmail(employee.getEmail());
                dtoCard.setStatus(employee.getEmployeeStatus().toString());
                list.add(dtoCard);
            }
            return list;
        }else{
            throw new Exception("Users not found!");
        }
    }
}
