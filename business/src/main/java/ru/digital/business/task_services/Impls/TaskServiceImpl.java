package ru.digital.business.task_services.Impls;


import lombok.extern.slf4j.Slf4j;
import ru.digital.dao.employee_dao.EmployeeRepository;
import ru.digital.models.employee_model.Employee;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.commons.enity_statuses.TaskStatus;
import ru.digital.commons.exceptions.employee_exceptions.EmployeeAlreadyDeletedException;
import ru.digital.commons.exceptions.employee_exceptions.EmployeeNotFoundException;
import ru.digital.commons.exceptions.project_exceptions.NotFoundProjectException;
import ru.digital.dao.project_dao.ProjectRepository;
import ru.digital.models.project_model.Project;
import ru.digital.business.task_services.TaskMapper;
import ru.digital.business.task_services.TaskService;
import ru.digital.dao.task_dao.TaskRepository;
import ru.digital.dao.task_dao.specifications.TaskSpecifications;
import ru.digital.dto.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import ru.digital.dto.task_dto.request_task_dto.CreateTaskDto;
import ru.digital.dto.task_dto.request_task_dto.SearchTaskDto;
import ru.digital.dto.task_dto.request_task_dto.UpdateTaskDto;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import ru.digital.models.task_model.Task;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.digital.commons.exceptions.task_exceptions.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@Slf4j
public class TaskServiceImpl implements TaskService {
    private TaskRepository repository;
    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String rabbitExchangeName;
    @Value("${rabbitmq.routingkey}")
    private String rabbitRoutingKey;

    @Autowired
    public TaskServiceImpl(TaskRepository repository, EmployeeRepository employeeRepository,
                           ProjectRepository projectRepository, RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.rabbitTemplate = rabbitTemplate;
    }


    public TaskCardDto createNewTask(CreateTaskDto dto) throws Exception {
        if (dto == null) {
            throw new NullTaskDtoException();
        }
        Task task = new Task();
        if (Objects.equals(dto.getTaskName(), "") || dto.getTaskName() == null) {
            throw new EmptyTaskNameException();
        }
        task.setTaskName(dto.getTaskName());
        task.setTaskDescription(dto.getTaskDescription());

        Optional<Employee> employee = employeeRepository.findById(dto.getExecutorId());
        if (employee.isPresent()) {
            if (employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE) {
                task.setExecutor(employee.get());
            } else {
                throw new EmployeeAlreadyDeletedException();
            }
        } else {
            throw new EmployeeNotFoundException();
        }


        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectCodeName());
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            task.setProject(project);
        } else {
            throw new NotFoundProjectException();
        }

        task.setHours(dto.getHours());
        Date now = new Date();
        if (TimeUnit.MILLISECONDS.toHours(dto.getDeadLineTime().getTime() - now.getTime()) >= dto.getHours()) {
            task.setDeadLineTime(dto.getDeadLineTime());
            task.setHours(dto.getHours());
            task.setStartTaskTime(now);
            task.setEditTaskTime(now);
        } else {
            throw new TooLessTimeTaskException();
        }

        task.setTaskStatus(TaskStatus.NEW);
        Optional<Employee> author = employeeRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!author.isPresent()) {
            throw new EmployeeNotFoundException();
        }
        task.setAuthor(author.get());
        task = repository.save(task);
        log.info("Created task with id: " + task.getTaskId().toString());
        rabbitTemplate.convertAndSend(rabbitExchangeName,rabbitRoutingKey,task);
        return TaskMapper.getTaskCardDto(task);
    }

    public TaskCardDto changeTask(UpdateTaskDto dto) throws Exception {
        if (dto == null) {
            throw new NullTaskDtoException();
        }
        Optional<Task> optionalTask = repository.findById(dto.getTaskId());
        if (optionalTask.isPresent()) {
            Task task = optionalTask.get();
            if (Objects.equals(dto.getTaskName(), "") || dto.getTaskName() == null) {
                throw new EmptyTaskNameException();
            }
            task.setTaskName(dto.getTaskName());
            task.setTaskDescription(dto.getTaskDescription());

            Optional<Project> optionalProject = projectRepository.findById(dto.getProjectCodeName());
            if (optionalProject.isPresent()) {
                Project project = optionalProject.get();
                task.setProject(project);
            } else {
                throw new NotFoundProjectException();
            }

            Optional<Employee> employee = employeeRepository.findById(dto.getExecutorId());
            if (employee.isPresent()) {
                if (employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE) {
                    task.setExecutor(employee.get());
                } else {
                    throw new EmployeeAlreadyDeletedException();
                }
            } else {
                throw new EmployeeNotFoundException();
            }
            if (TimeUnit.MILLISECONDS.toHours(dto.getDeadLineTime().getTime() - task.getStartTaskTime().getTime()) >= dto.getHours()) {
                task.setDeadLineTime(dto.getDeadLineTime());
                task.setHours(dto.getHours());
            } else {
                throw new TooLessTimeTaskException();
            }
            Optional<Employee> author = employeeRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            if (!author.isPresent()) {
                throw new EmployeeNotFoundException();
            }
            task.setAuthor(author.get());
            task = repository.save(task);
            log.info("Task with id = " + task.getTaskId() + " was updated!");
            rabbitTemplate.convertAndSend(rabbitExchangeName,rabbitRoutingKey,task);
            return TaskMapper.getTaskCardDto(task);
        } else {
            throw new NotFoundTaskException();
        }


    }


    public List<TaskCardDto> searchTask(SearchTaskDto dto) throws Exception {
        if (dto == null) {
            throw new NullTaskDtoException();
        }
        Employee author = null;
        Employee executor = null;
        if (dto.getAuthorId() != null) {
            Optional<Employee> optionalAuthor = employeeRepository.findById(dto.getAuthorId());
            if (optionalAuthor.isPresent()) {
                author = optionalAuthor.get();
            }
        }

        if (dto.getExecutorId() != null) {
            Optional<Employee> optionalEx = employeeRepository.findById(dto.getExecutorId());
            if (optionalEx.isPresent()) {
                executor = optionalEx.get();
            }
        }
        List<Task> tasks = repository.findAll(TaskSpecifications.searchTask(
                        dto.getTextFilter(),
                        dto.getStatuses(),
                        executor,
                        author,
                        dto.getDeadLineTimeStart(),
                        dto.getDeadLineTimeEnd(),
                        dto.getStartTaskTimeStart(),
                        dto.getStartTaskTimeEnd()
                )
        );
        List<TaskCardDto> cardDtos = new ArrayList<>();
        for (var task : tasks) {
            cardDtos.add(TaskMapper.getTaskCardDto(task));
        }
        return cardDtos;
    }


    public TaskCardDto changeTaskStatus(ChangeStatusOfTaskDto dto) throws Exception {
        if (dto == null) {
            throw new NullTaskDtoException();
        }
        Optional<Task> taskOptional = repository.findById(dto.getTaskId());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            if (checkAvailableToChangeStatus(task.getTaskStatus(),
                    dto.getTaskStatus())) {
                task.setTaskStatus(dto.getTaskStatus());
                log.info("Task with id = " + task.getTaskId() + " has new status: " +
                        task.getTaskStatus().toString());
                task = repository.save(task);
                return TaskMapper.getTaskCardDto(task);
            } else {
                log.error("Not availbale status: " +
                        dto.getTaskStatus() + " for task with id: " + task.getTaskId().toString());
                throw new NotAvailableTaskStatusException();
            }
        } else {
            throw new NotFoundTaskException();
        }
    }


    private boolean checkAvailableToChangeStatus(TaskStatus currentStatus, TaskStatus newStatus) throws Exception {
        switch (currentStatus) {
            case NEW -> {
                return newStatus == TaskStatus.INPROGRESS;
            }
            case INPROGRESS -> {
                return newStatus == TaskStatus.DONE;
            }
            case DONE -> {
                return newStatus == TaskStatus.CLOSED;
            }
            case CLOSED -> {
                return false;
            }
        }
        throw new NotCorrectTaskStatusException();
    }

}
