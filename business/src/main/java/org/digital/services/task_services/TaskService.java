package org.digital.services.task_services;

import jakarta.transaction.Transactional;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.enity_statuses.TaskStatus;
import org.digital.exceptions.employee_exceptions.EmployeeAlreadyDeletedException;
import org.digital.exceptions.employee_exceptions.EmployeeNotFoundException;
import org.digital.exceptions.task_exceptions.*;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_model.Project;
import org.digital.task_dao.TaskRepository;
import org.digital.task_dao.specifications.TaskSpecifications;
import org.digital.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import org.digital.task_dto.request_task_dto.CreateTaskDto;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_dto.request_task_dto.UpdateTaskDto;
import org.digital.task_dto.response_task_dto.TaskCardDto;
import org.digital.task_model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class TaskService {
    private TaskRepository repository;
    private EmployeeRepository employeeRepository;

    @Autowired
    public TaskService(TaskRepository repository, EmployeeRepository employeeRepository) {
        this.repository = repository;
        this.employeeRepository = employeeRepository;
    }


    public TaskCardDto createNewTask(CreateTaskDto dto) throws Exception {
        if(dto == null){
            throw new NullTaskDtoException();
        }
        Task task = new Task();
        if(Objects.equals(dto.getTaskName(),"")){
            throw new EmptyTaskNameException();
        }
        task.setTaskName(dto.getTaskName());
        task.setTaskDescription(dto.getTaskDescription());

        try{
            Optional<Employee> employee = employeeRepository.findById(dto.getExecutorId());
            if(employee.isPresent()){
                if(employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE){
                    task.setExecutor(employee.get());
                }else{
                    throw new EmployeeAlreadyDeletedException();
                }
            }

        }catch (Exception ex){
            throw new EmployeeNotFoundException();
        }

        task.setHours(dto.getHours());
        Date now = new Date();
        if(now.getHours() - dto.getDeadLineTime().getHours() >= dto.getHours()){
            task.setDeadLineTime(dto.getDeadLineTime());
            task.setHours(dto.getHours());
            task.setStartTaskTime(now);
            task.setEditTaskTime(now);
        }else{
            throw new TooLessTimeTaskException();
        }

        task.setTaskStatus(TaskStatus.NEW);
        Optional<Employee> author = employeeRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        if(!author.isPresent()){
            throw new EmployeeNotFoundException();
        }
        task.setAuthor(author.get());
        repository.save(task);
        return TaskMapper.getTaskCardDto(task);
    }

    public TaskCardDto changeTask(UpdateTaskDto dto) throws Exception {
        if(dto == null){
            throw new NullTaskDtoException();
        }
        Optional<Task> optionalTask = repository.findById(dto.getTaskId());
        if(optionalTask.isPresent()){
            Task task = new Task();
            if(Objects.equals(dto.getTaskName(),"")){
                throw new EmptyTaskNameException();
            }
            task.setTaskName(dto.getTaskName());
            task.setTaskDescription(dto.getTaskDescription());

            try{
                Optional<Employee> employee = employeeRepository.findById(dto.getExecutorId());
                if(employee.isPresent()){
                    if(employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE){
                        task.setExecutor(employee.get());
                    }else{
                        throw new EmployeeAlreadyDeletedException();
                    }
                }

            }catch (Exception ex){
                throw new EmployeeNotFoundException();
            }

            if(dto.getDeadLineTime().getHours() - task.getStartTaskTime().getHours() >= dto.getHours()){
                task.setDeadLineTime(dto.getDeadLineTime());
                task.setHours(dto.getHours());
            }else{
                throw new TooLessTimeTaskException();
            }
            Optional<Employee> author = employeeRepository.findByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            if(!author.isPresent()){
                throw new EmployeeNotFoundException();
            }
            task.setAuthor(author.get());
            repository.save(task);
            return TaskMapper.getTaskCardDto(task);
        }else {
            throw new NotFoundTaskException();
        }


    }


    public List<TaskCardDto> searchTask(SearchTaskDto dto) throws Exception {
        if(dto == null){
            throw new NullTaskDtoException();
        }
        Employee author = null;
        Employee executor = null;
        Optional<Employee> optionalAuthor = employeeRepository.findById(Long.parseLong(dto.getAuthorId()));
        if(optionalAuthor.isPresent()){
            author = optionalAuthor.get();
        }
        Optional<Employee> optionalEx = employeeRepository.findById(Long.parseLong(dto.getExecutorId()));
        if(optionalEx.isPresent()){
            executor = optionalEx.get();
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
        for(var task : tasks){
            cardDtos.add(TaskMapper.getTaskCardDto(task));
        }
        return cardDtos;
    }


    public TaskCardDto changeTaskStatus(ChangeStatusOfTaskDto dto) throws Exception {
        if(dto == null){
            throw new NullTaskDtoException();
        }
        Optional<Task> taskOptional = repository.findById(dto.getTaskId());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            if (checkAvailableToChangeStatus(task.getTaskStatus(),
                  TaskStatus.valueOf(dto.getTaskStatus()))) {
                task.setTaskStatus(TaskStatus.valueOf(dto.getTaskStatus()));
                repository.save(task);
                return TaskMapper.getTaskCardDto(task);
            } else {
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
