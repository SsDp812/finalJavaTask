package org.digital.services.user_services;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.annotation.PostConstruct;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.exceptions.employee_exceptions.EmployeeNotFoundException;
import org.digital.services.employee_services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private EmployeeRepository repository;

    @Autowired
    public UserService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = repository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return new User(username, employee.getPassword(), Collections.emptyList());
    }

    @PostConstruct
    public void initAdmin(){
        repository.findByLogin("root")
                .orElse(repository.save(new Employee(
                        Long.valueOf("0"),
                        "Surname",
                        "Name",
                        "Middlename",
                        "admin",
                        "user",
                        "root",
                        "admin@gmail.ru",
                        EmployeeStatus.ACTIVE
                )));
    }
}
