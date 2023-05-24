package org.digital.services.task_services;

import jakarta.transaction.Transactional;
import org.digital.employee_dao.EmployeeRepository;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.ProjectStatus;
import org.digital.enity_statuses.TaskStatus;
import org.digital.project_dto.request_project_dto.ChangeProjectStatusDto;
import org.digital.project_model.Project;
import org.digital.task_dao.TaskRepository;
import org.digital.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import org.digital.task_dto.request_task_dto.CreateTaskDto;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_dto.request_task_dto.UpdateTaskDto;
import org.digital.task_dto.response_task_dto.TaskCardDto;
import org.digital.task_model.Task;
import org.springframework.beans.factory.annotation.Autowired;
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


    public void createNewTask(CreateTaskDto dto) throws Exception {
        Task task = new Task();
        if(!Objects.equals(dto.getTaskName(),"")){
            task.setTaskName(dto.getTaskName());
        }else{
            throw new Exception("Empty task name!");
        }


        task.setTaskDescription(dto.getTaskDescription());


        if(!Objects.equals(dto.getExecutorId(),"")){
            try{
                Optional<Employee> employee = employeeRepository.findById(Long.parseLong(dto.getExecutorId()));
                if(employee.isPresent()){
                    if(employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE){
                        task.setExecutor(employee.get());
                    }else{
                        throw new Exception("Employee was deleted");
                    }
                }

            }catch (Exception ex){
                throw new Exception("Error employee id");
            }
        }

        task.setHours(dto.getHours());
        Date now = new Date();
        if(now.getHours() - dto.getDeadLineTime().getHours() >= dto.getHours()){
            task.setDeadLineTime(dto.getDeadLineTime());
            task.setHours(dto.getHours());
            task.setStartTaskTime(now);
            task.setEditTaskTime(now);
        }else{
            throw new Exception("to less hours for this task!");
        }

        task.setTaskStatus(TaskStatus.NEW);
        //task.setAuthor(); //TODO set Author from session after create
    }

    public void changeTask(UpdateTaskDto dto) throws Exception {
        Optional<Task> optionalTask = repository.findById(dto.getTaskId());
        if(optionalTask.isPresent()){
            Task task = new Task();
            if(!Objects.equals(dto.getTaskName(),"")){
                task.setTaskName(dto.getTaskName());
            }

            task.setTaskDescription(dto.getTaskDescription());

            if(!Objects.equals(dto.getExecutorId(),"")){
                try{
                    Optional<Employee> employee = employeeRepository.findById(Long.parseLong(dto.getExecutorId()));
                    if(employee.isPresent()){
                        if(employee.get().getEmployeeStatus() == EmployeeStatus.ACTIVE){
                            task.setExecutor(employee.get());
                        }else{
                            throw new Exception("Employee was deleted");
                        }
                    }

                }catch (Exception ex){
                    throw new Exception("Error employee id");
                }
            }

            if(dto.getDeadLineTime().getHours() - task.getStartTaskTime().getHours() >= dto.getHours()){
                task.setDeadLineTime(dto.getDeadLineTime());
                task.setHours(dto.getHours());
            }else{
                throw new Exception("to less time for this task!");
            }
            repository.save(task);
        }else {
            throw new Exception("Task not found, error id!");
        }

        //task.setAuthor(); //TODO set Author from session after update
    }


    public List<TaskCardDto> searchTask(SearchTaskDto dto) throws Exception {
        Employee author = null;
        Employee executor = null;
        try {
            Optional<Employee> optionalAuthor = employeeRepository.findById(Long.parseLong(dto.getAuthorId()));
            if(optionalAuthor.isPresent()){
                author = optionalAuthor.get();
            }
        }catch (Exception ex){

        }

        try {
            Optional<Employee> optionalEx = employeeRepository.findById(Long.parseLong(dto.getExecutorId()));
            if(optionalEx.isPresent()){
               executor = optionalEx.get();
            }
        }catch (Exception ex){

        }

        Optional<List<Task>> optionalTasks = repository.findByTaskNameContainingIgnoreCaseAndTaskStatusInAndExecutorOrAuthorAndDeadLineTimeBetweenOrStartTaskTimeBetweenOrderByEditTaskTimeDesc(
                dto.getTextFilter(),
                dto.getStatuses(),
                executor,
                author,
                dto.getDeadLineTimeStart(),
                dto.getDeadLineTimeEnd(),
                dto.getStartTaskTimeStart(),
                dto.getStartTaskTimeEnd()
        );
        if(optionalTasks.isPresent()){
            List<Task> tasks = optionalTasks.get();
            List<TaskCardDto> cardDtos = new ArrayList<>();
            for(var task : tasks){
                TaskCardDto tempDto = new TaskCardDto();
                tempDto.setTaskName(task.getTaskName());
                tempDto.setTaskDescription(task.getTaskDescription());
                tempDto.setHours(task.getHours());

                tempDto.setExecutor(new EmployeeCardDto(
                        task.getExecutor().getSurname(),
                        task.getExecutor().getName(),
                        task.getExecutor().getMiddleName(),
                        task.getExecutor().getJobTitle(),
                        task.getExecutor().getEmail(),
                        task.getExecutor().getEmployeeStatus().toString()
                ));

                tempDto.setAuthor(
                        new EmployeeCardDto(
                                task.getAuthor().getSurname(),
                                task.getAuthor().getName(),
                                task.getAuthor().getMiddleName(),
                                task.getAuthor().getJobTitle(),
                                task.getAuthor().getEmail(),
                                task.getAuthor().getEmployeeStatus().toString()
                        )
                );
                tempDto.setTaskStatus(task.getTaskStatus());
                tempDto.setStartTaskTime(task.getStartTaskTime());
                tempDto.setDeadLineTime(task.getDeadLineTime());
                tempDto.setEditTaskTime(task.getEditTaskTime());
                cardDtos.add(tempDto);
            }
            return cardDtos;
        }else{
            throw new Exception("Tasks not found!");
        }
    }


    public void changeTaskStatus(ChangeStatusOfTaskDto dto) throws Exception {
        Optional<Task> taskOptional = repository.findById(dto.getTaskId());
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();

            if (checkAvailableToChangeStatus(task.getTaskStatus(),
                  TaskStatus.valueOf(dto.getTaskStatus()))) {
                task.setTaskStatus(TaskStatus.valueOf(dto.getTaskStatus()));
                repository.save(task);
            } else {
                throw new Exception("You cant set this status");
            }
        } else {
            throw new Exception("Error task id, task not found!");
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
        throw new Exception("Error status");
    }

}
