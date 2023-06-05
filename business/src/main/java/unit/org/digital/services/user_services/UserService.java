package unit.org.digital.services.user_services;

import jakarta.annotation.PostConstruct;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private EmployeeRepository repository;

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(EmployeeRepository repository, BCryptPasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = repository.findByLogin(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return new User(username, employee.getPassword(), Collections.emptyList());
    }

    @PostConstruct
    public void initAdmin(){
        Optional<Employee> optionalEmployee = repository.findByLogin("ROOT");

        if(!optionalEmployee.isPresent()){
            repository.save(new Employee(
                    Long.valueOf("0"),
                    "Surname",
                    "Name",
                    "Middlename",
                    "admin",
                    "ROOT",
                    passwordEncoder.encode("root"),
                    "admin@gmail.ru",
                    EmployeeStatus.ACTIVE
            ));
        }
    }
}
