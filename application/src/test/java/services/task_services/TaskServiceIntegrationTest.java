package services.task_services;

import org.digital.Main;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.request_employee_dto.CreateEmployeeDto;
import org.digital.employee_dto.request_employee_dto.DeleteEmployeeDto;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.TaskStatus;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_model.Project;
import org.digital.services.employee_services.Impls.EmployeeServiceImpl;
import org.digital.services.task_services.Impls.TaskServiceImpl;
import org.digital.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import org.digital.task_dto.request_task_dto.CreateTaskDto;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_dto.request_task_dto.UpdateTaskDto;
import org.digital.task_dto.response_task_dto.TaskCardDto;
import org.digital.task_model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import services.BaseTest;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest(classes = Main.class)
public class TaskServiceIntegrationTest extends BaseTest {

    @Autowired
    private TaskServiceImpl service;
    @Autowired
    private EmployeeServiceImpl employeeService;

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void addUserToBaseForTest(){
        Optional<Employee> optionalEmployee = employeeRepository.findByLogin("login");
        Random random = new Random();
        if(!optionalEmployee.isPresent()){
            employeeRepository.save(new Employee(
                    Long.valueOf(1000 + random.nextInt()),
                    "Ivanov",
                    "Ivan",
                    "Ivanovich",
                    "IT",
                    "login",
                    "password",
                    "email",
                    EmployeeStatus.ACTIVE
            ));
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void createNewTask() throws Exception {
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        Task task = getSomeTask();
        task.setExecutor(employee);
        Project project = getSomeProject();
        projectRepository.save(project);
        task.setProject(project);
        TaskCardDto dto = service.createNewTask(getCreateDto(task));
        Assertions.assertEquals(task.getTaskName(), dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(), dto.getTaskDescription());
        Assertions.assertEquals(task.getAuthor().getName(), dto.getAuthor().getName());
    }


    @Test
    @WithMockUser(username = "login")
    public void createNewTaskByNullDto() {
        try {
            service.createNewTask(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Null task dto!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void createNewTaskWithEmptyName() {
        try {
            CreateTaskDto dto = getCreateDto(getSomeTask());
            dto.setTaskName("");
            service.createNewTask(dto);
        } catch (Exception ex) {
            Assertions.assertEquals("Empty task name exception!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void createNewTaskWithErrorEmployeeId() throws Exception {
        try {
            Task task = getSomeTask();
            Employee employee = getSomeEmloyee();
            Random random = new Random();
            employee.setAccountId(Long.valueOf(1000 + random.nextInt()));
            task.setExecutor(employee);
            CreateTaskDto dto = getCreateDto(task);
            service.createNewTask(getCreateDto(task));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was not found!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void createNewTaskWithDeletedEmployee() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            employeeService.deleteEmployee(new DeleteEmployeeDto(employee.getAccountId()));
            Task task = getSomeTask();
            Project project = getSomeProject();
            projectRepository.save(project);
            task.setProject(project);
            task.setExecutor(employee);
            Employee applicationUser = employee;
            TaskCardDto dto = service.createNewTask(getCreateDto(task));
        } catch (Exception ex) {
            Assertions.assertEquals("Employee was deleted!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void createNewTaskWithLessTime() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            Task task = getSomeTask();
            task.setDeadLineTime(new Date());
            Project project = getSomeProject();
            projectRepository.save(project);
            task.setProject(project);
            task.setExecutor(employee);
            TaskCardDto dto = service.createNewTask(getCreateDto(task));

        } catch (Exception ex) {
            Assertions.assertEquals("Too less time for this task!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void changeTask() throws Exception {
        Employee employee = getSomeEmloyee();
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        Task task = getSomeTask();
        task.setExecutor(employee);
        Project project = getSomeProject();
        projectRepository.save(project);
        task.setProject(project);
        TaskCardDto taskCardDto = service.createNewTask(getCreateDto(task));
        task.setTaskDescription(UUID.randomUUID().toString());
        TaskCardDto dto = service.changeTask(getUpdateDto(task));
        Assertions.assertEquals(task.getTaskName(), dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(), dto.getTaskDescription());
        Assertions.assertEquals(task.getAuthor().getName(), dto.getAuthor().getName());
    }


    @Test
    @WithMockUser(username = "login")
    public void changeTaskByNullDto() {
        try {
            service.changeTask(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Null task dto!", ex.getMessage());
        }
    }

    @Test
    @WithMockUser(username = "login")
    public void changeTaskWithEmptyName() {
        try {
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            Task task = getSomeTask();
            task.setExecutor(employee);
            Project project = getSomeProject();
            projectRepository.save(project);
            task.setProject(project);
            TaskCardDto taskCardDto = service.createNewTask(getCreateDto(task));
            task.setTaskName("");
            TaskCardDto dto = service.changeTask(getUpdateDto(task));
        } catch (Exception ex) {
            Assertions.assertEquals("Empty task name exception!", ex.getMessage());
        }
    }


    @Test
    @WithMockUser(username = "login")
    public void searchTask() throws Exception {
        Task task = getSomeTask();
        Project project = getSomeProject();
        projectRepository.save(project);
        task.setProject(project);
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));

        employee.setAccountId(employeeCardDto.getId());
        task.setExecutor(employee);
        TaskCardDto dto = service.createNewTask(getCreateDto(task));
        List<TaskCardDto> tasks = service.searchTask(new SearchTaskDto(
                task.getTaskName(),
                Arrays.asList(TaskStatus.NEW),
                null,
                null,
                null,
                null,
                null,
                null
        ));
        Assertions.assertEquals(task.getTaskName(), tasks.get(0).getTaskName());
        Assertions.assertEquals(task.getTaskDescription(), tasks.get(0).getTaskDescription());
    }

    @Test
    @WithMockUser(username = "login")
    public void changeStatusTaskToAvailableStatus() throws Exception {
        Task task = getSomeTask();
        Project project = getSomeProject();
        projectRepository.save(project);
        task.setProject(project);
        Employee employee = getSomeEmloyee();
        employee.setLogin(UUID.randomUUID().toString());
        EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
        employee.setAccountId(employeeCardDto.getId());
        task.setExecutor(employee);
        TaskCardDto dtoTask = service.createNewTask(getCreateDto(task));
        task.setTaskId(dtoTask.getTaskId());
        TaskCardDto dto = service.changeTaskStatus(new ChangeStatusOfTaskDto(
                dtoTask.getTaskId(),
                TaskStatus.INPROGRESS.toString()
        ));
        Assertions.assertEquals(task.getTaskName(), dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(), dto.getTaskDescription());
        Assertions.assertEquals(TaskStatus.INPROGRESS.toString(),dto.getTaskStatus().toString());
    }


    @Test
    @WithMockUser(username = "login")
    public void changeStatusTaskToNotAvailableStatus() {
        try {
            Task task = getSomeTask();
            Project project = getSomeProject();
            projectRepository.save(project);
            task.setProject(project);
            Employee employee = getSomeEmloyee();
            employee.setLogin(UUID.randomUUID().toString());
            EmployeeCardDto employeeCardDto = employeeService.createNewEmployee(getCreateEmployeeDto(employee));
            employee.setAccountId(employeeCardDto.getId());
            task.setExecutor(employee);
            TaskCardDto dtoTask = service.createNewTask(getCreateDto(task));
            task.setTaskId(dtoTask.getTaskId());
            TaskCardDto dto = service.changeTaskStatus(new ChangeStatusOfTaskDto(
                    task.getTaskId(),
                    TaskStatus.DONE.toString()
            ));
        } catch (Exception ex) {
            Assertions.assertEquals("Not available task status!", ex.getMessage());
        }
    }



    private CreateTaskDto getCreateDto(Task task) {
        return new CreateTaskDto(
                task.getTaskName(),
                task.getTaskDescription(),
                task.getProject().getProjectCodeName(),
                task.getExecutor().getAccountId(),
                task.getHours(),
                task.getDeadLineTime(),
                task.getStartTaskTime()
        );
    }


    private UpdateTaskDto getUpdateDto(Task task) {
        return new UpdateTaskDto(
                Long.parseLong("1"),
                task.getTaskName(),
                task.getProject().getProjectCodeName(),
                task.getTaskDescription(),
                task.getExecutor().getAccountId(),
                task.getHours(),
                task.getDeadLineTime()
        );
    }

    private CreateEmployeeDto getCreateEmployeeDto(Employee employee) {
        return new CreateEmployeeDto(
                employee.getSurname(),
                employee.getName(),
                employee.getMiddleName(),
                employee.getJobTitle(),
                employee.getEmail(),
                employee.getLogin(),
                employee.getPassword()
        );
    }
}
