package services;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;
import ru.digital.application.Main;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.commons.enity_statuses.ProjectStatus;
import ru.digital.commons.enity_statuses.TaskStatus;
import ru.digital.models.employee_model.Employee;
import ru.digital.models.project_model.Project;
import ru.digital.models.task_model.Task;

import java.util.Date;
import java.util.UUID;

@SpringBootTest(classes = Main.class, properties = {"rabbitmq.queue=myQueue", "rabbitmq.routingkey=myKey",
        "rabbitmq.exchange=mails-exchange", "spring.rabbitmq.username=admin", "spring.rabbitmq.password=admin",
        "spring.rabbitmq.host.port=5672", "upload.path=/files", "action.notification.cron=0 0 11 * * ?"
})
public class BaseTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres");
    ;

    protected static RabbitMQContainer rabbit = new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.10.7-management"))
            .withAdminPassword("admin")
            .withAccessToHost(true)
            .withUser("admin", "admin")
            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        postgres.start();
        rabbit.start();
    }

    @AfterAll
    static void afterAll() {
        //postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbit::getHost);
        registry.add("spring.rabbitmq.host.port", rabbit::getAmqpPort);


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
                TaskStatus.NEW,
                null,
                null,
                null
        );
    }
}
