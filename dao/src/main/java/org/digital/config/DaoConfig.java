package org.digital.config;

import org.digital.employee_dao.EmployeeRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.digital.dao")
@EnableJpaRepositories("org.digital")
public class DaoConfig {
}
