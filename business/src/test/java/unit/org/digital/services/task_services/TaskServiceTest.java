package unit.org.digital.services.task_services;


import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.enity_statuses.TaskStatus;
import org.digital.project_dao.ProjectRepository;
import org.digital.project_model.Project;
import org.digital.task_dao.TaskRepository;
import org.digital.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import org.digital.task_dto.request_task_dto.CreateTaskDto;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_dto.request_task_dto.UpdateTaskDto;
import org.digital.task_dto.response_task_dto.TaskCardDto;
import org.digital.task_model.Task;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private TaskService taskService;


    @Test
    @WithMockUser(username = "login")
    public void createNewTask() throws Exception {
        Employee employee = getSomeEmloyee();
        Task task = getSomeTask();

        Employee applicationUser = employee;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(employeeRepository.findById(employee.getAccountId()))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.findByLogin(any())).thenReturn(Optional.of(employee));
        when(projectRepository.findById(task.getProject().getProjectCodeName()))
                .thenReturn(Optional.ofNullable(task.getProject()));
        when(taskRepository.save(any())).thenReturn(task);
        TaskCardDto dto = taskService.createNewTask(getCreateDto(task));

        Assertions.assertEquals(task.getTaskName(),dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(),dto.getTaskDescription());
        Assertions.assertEquals(task.getAuthor().getName(),dto.getAuthor().getName());
    }

    @Test
    public void createNewTaskByNullDto(){
        try{
            taskService.createNewTask(null);
        } catch (Exception ex) {
            Assertions.assertEquals("Null task dto!",ex.getMessage());
        }
    }

    @Test
    public void createNewTaskWithEmptyName(){
        try {
            CreateTaskDto dto = getCreateDto(getSomeTask());
            dto.setTaskName("");
            taskService.createNewTask(dto);
        } catch (Exception ex) {
            Assertions.assertEquals("Empty task name exception!",ex.getMessage());
        }
    }

    @Test
    public void createNewTaskWithErrorEmployeeId() throws Exception {
        try{
            Employee employee = getSomeEmloyee();
            Task task = getSomeTask();

            Employee applicationUser = employee;
            when(employeeRepository.findById(employee.getAccountId()))
                    .thenReturn(Optional.ofNullable(null));
            TaskCardDto dto = taskService.createNewTask(getCreateDto(task));
        }catch (Exception ex){
            Assertions.assertEquals("Employee was not found!",ex.getMessage());
        }
    }

    @Test
    public void createNewTaskWithDeletedEmployee(){
        try{
            Employee employee = getSomeEmloyee();
            employee.setEmployeeStatus(EmployeeStatus.DELETED);
            Task task = getSomeTask();

            Employee applicationUser = employee;
            when(employeeRepository.findById(employee.getAccountId()))
                    .thenReturn(Optional.ofNullable(employee));
            TaskCardDto dto = taskService.createNewTask(getCreateDto(task));
        }catch (Exception ex){
            Assertions.assertEquals("Employee was deleted!",ex.getMessage());
        }
    }

    @Test
    public void createNewTaskWithLessTime(){
        try{
            Employee employee = getSomeEmloyee();
            Task task = getSomeTask();
            task.setDeadLineTime(new Date());
            Employee applicationUser = employee;
            when(employeeRepository.findById(employee.getAccountId()))
                    .thenReturn(Optional.of(employee));
            when(projectRepository.findById(task.getProject().getProjectCodeName()))
                    .thenReturn(Optional.ofNullable(task.getProject()));
            TaskCardDto dto = taskService.createNewTask(getCreateDto(task));

        }catch (Exception ex){
            Assertions.assertEquals("Too less time for this task!",ex.getMessage());
        }
    }

    @Test
    public void changeTask() throws Exception {
        Employee employee = getSomeEmloyee();
        Task task = getSomeTask();
        Task oldTask = getSomeTask();
        oldTask.setTaskName("task");
        Employee applicationUser = employee;
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(oldTask));
        when(employeeRepository.findById(employee.getAccountId()))
                .thenReturn(Optional.of(employee));
        when(employeeRepository.findByLogin(any())).thenReturn(Optional.of(employee));

        when(projectRepository.findById(task.getProject().getProjectCodeName()))
                .thenReturn(Optional.ofNullable(task.getProject()));
        when(taskRepository.save(task)).thenReturn(task);
        TaskCardDto dto = taskService.changeTask(getUpdateDto(task));

        Assertions.assertEquals(task.getTaskName(),dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(),dto.getTaskDescription());
        Assertions.assertEquals(task.getAuthor().getName(),dto.getAuthor().getName());
    }


    @Test
    public void changeTaskByNullDto(){
        try {
            taskService.changeTask(null);
        }catch (Exception ex){
            Assertions.assertEquals("Null task dto!",ex.getMessage());
        }
    }

    @Test
    public void changeTaskWithEmptyName(){
        try{
            Employee employee = getSomeEmloyee();
            Task task = getSomeTask();
            Task oldTask = getSomeTask();
            task.setTaskName("");
            Employee applicationUser = employee;
            when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(oldTask));
            TaskCardDto dto = taskService.changeTask(getUpdateDto(task));
        }catch (Exception ex){
            Assertions.assertEquals("Empty task name exception!",ex.getMessage());
        }
    }


    @Test
    public void searchTask() throws Exception {
        Task task = getSomeTask();
        Mockito.when(employeeRepository.findById(any()))
                .thenReturn(Optional.ofNullable(task.getAuthor()));
        Mockito.when(taskRepository.findAll((Specification<Task>) any())).thenReturn(Arrays.asList(task));
        List<TaskCardDto> tasks = taskService.searchTask(new SearchTaskDto(
            task.getTaskName(),
                Arrays.asList(TaskStatus.NEW),
                "1",
                "1",
                null,
                null,
                null,
                null
        ));
        Assertions.assertEquals(task.getTaskName(),tasks.get(0).getTaskName());
        Assertions.assertEquals(task.getTaskDescription(),tasks.get(0).getTaskDescription());
    }

    @Test
    public void changeStatusTaskToAvailableStatus() throws Exception {
        Task task = getSomeTask();
        Task newTask = getSomeTask();
        newTask.setTaskStatus(TaskStatus.INPROGRESS);
        Mockito.when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(newTask)).thenReturn(newTask);
        TaskCardDto dto = taskService.changeTaskStatus(new ChangeStatusOfTaskDto(
                task.getTaskId(),
                TaskStatus.INPROGRESS.toString()
        ));
        Assertions.assertEquals(task.getTaskName(),dto.getTaskName());
        Assertions.assertEquals(task.getTaskDescription(),dto.getTaskDescription());
    }


    @Test
    public void changeStatusTaskToNotAvailableStatus(){
        try {
            Task task = getSomeTask();
            Task newTask = getSomeTask();
            Mockito.when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
            TaskCardDto dto = taskService.changeTaskStatus(new ChangeStatusOfTaskDto(
                    task.getTaskId(),
                    TaskStatus.DONE.toString()
            ));
        }catch (Exception ex){
            Assertions.assertEquals("Not available task status!",ex.getMessage());
        }
    }

    private Employee getSomeEmloyee(){
        Employee employee = new Employee(
                Long.valueOf(1),
                "Ivanov",
                "Ivan",
                "Ivanovich",
                "IT",
                "login",
                "password",
                "email",
                EmployeeStatus.ACTIVE
        );
        return employee;
    }

    private Task getSomeTask(){
        return new Task(
                Long.parseLong("1"),
                getSomeProject(),
                "TaskName",
                "Decsc",
                getSomeEmloyee(),
                20,
                new Date(124,4,12,11,0,0),
                getSomeEmloyee(),
                new Date(123,2,21,10,0,0),
                new Date(123,2,23,10,0,0),
                TaskStatus.NEW
        );
    }

    private Project getSomeProject(){
        return new Project(
                "CodeName",
                "Name",
                "desc",
                ProjectStatus.DEVELOPING
        );
    }

    private CreateTaskDto getCreateDto(Task task){
        return new CreateTaskDto(
                task.getTaskName(),
                task.getTaskDescription(),
                task.getProject().getProjectCodeName(),
                task.getExecutor().getAccountId(),
                task.getHours(),
                task.getDeadLineTime(),
                task.getStartTaskTime(),
                task.getEditTaskTime()
        );
    }


    private UpdateTaskDto getUpdateDto(Task task){
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
}
