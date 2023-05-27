package org.digital.config;

import org.digital.employee_dao.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.digital.dao")
public class DaoConfig {
}
