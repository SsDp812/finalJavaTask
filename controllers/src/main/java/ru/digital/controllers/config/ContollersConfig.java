package ru.digital.controllers.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.digital.business.employee_services.EmployeeService;
import ru.digital.business.employee_services.Impls.EmployeeServiceImpl;
import ru.digital.controllers.employee_сontrollers.EmployeeController;

@Configuration
@ComponentScan("ru.digital.controllers")
public class ContollersConfig {

}
