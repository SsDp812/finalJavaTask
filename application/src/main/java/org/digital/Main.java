package org.digital;


import org.digital.config.AppConfig;
import org.digital.config.SecurityConfig;
import org.digital.employee_сontrollers.EmployeeController;
import org.digital.project_controllers.ProjectController;
import org.digital.task_controllers.TaskController;
import org.digital.team_controllers.TeamController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class,args);
    }
}