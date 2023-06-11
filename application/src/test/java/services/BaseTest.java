package services;

import org.digital.Main;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.enity_statuses.TaskStatus;
import org.digital.project_model.Project;
import org.digital.task_model.Task;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.Date;
import java.util.UUID;

@SpringBootTest(classes = Main.class)
public class BaseTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");;
    @Container
    protected static RabbitMQContainer container = new RabbitMQContainer();
    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        //postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    protected Employee getSomeEmloyee() {
        Employee employee = new Employee(
                null,
                "Ivanov",
                "Ivan",
                "Ivanovich",
                "IT",
                UUID.randomUUID().toString(),
                "password",
                "email",
                EmployeeStatus.ACTIVE
        );
        return employee;
    }


    protected Project getSomeProject() {
        return new Project(
                UUID.randomUUID().toString(),
                "Name",
                "Decs",
                ProjectStatus.DRAFT
        );
    }


    protected Task getSomeTask() {
        return new Task(
                Long.parseLong("1"),
                getSomeProject(),
                "TaskName",
                "Decsc",
                getSomeEmloyee(),
                20,
                new Date(124, 4, 12, 11, 0, 0),
                getSomeEmloyee(),
                new Date(123, 2, 21, 10, 0, 0),
                new Date(123, 2, 23, 10, 0, 0),
                TaskStatus.NEW
        );
    }
}
