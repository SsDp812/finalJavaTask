package ru.digital.dao.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("ru.digital.dao")
@EnableJpaRepositories("ru.digital")
public class DaoConfig {
}
