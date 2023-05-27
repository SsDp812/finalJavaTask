package org.digital.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = {
        "dao"
})
@EntityScan(basePackages = {
        "org.digital.employee_model",
        "org.digital.project_model",
        "org.digital.task_model",
        "org.digital.team_member_model",
        "org.digital.team_model"
})
@EnableJpaRepositories(basePackages = {
        "org.digital.employee_dao",
        "org.digital.project_dao",
        "org.digital.task_dao",
        "org.digital.team_dao",
        "org.digital.team_member_dao"

})
public class AppConfig {
}
