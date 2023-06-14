package ru.digital.controllers.task_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.digital.business.task_services.TaskService;
import ru.digital.dto.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import ru.digital.dto.task_dto.request_task_dto.CreateTaskDto;
import ru.digital.dto.task_dto.request_task_dto.SearchTaskDto;
import ru.digital.dto.task_dto.request_task_dto.UpdateTaskDto;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "TaskController", description = "Controller for tasks")
public class TaskController {
    private TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Creating new task")
    @PostMapping(value = "/new")
    public TaskCardDto createTask(@RequestBody @Valid CreateTaskDto dto) {
        return service.createNewTask(dto);
    }

    @PostMapping(value = "/addFile")
    public void add(@RequestParam("file") MultipartFile file, @RequestParam("taskId") Long taskId) {
        service.loadFileToTask(file, taskId);
    }

    @Operation(summary = "Updating task info")
    @PostMapping(value = "/update")
    public TaskCardDto changeTask(@RequestBody @Valid UpdateTaskDto dto) {
        return service.changeTask(dto);
    }

    @Operation(summary = "Searching task by filter")
    @PostMapping(value = "/search")
    public List<TaskCardDto> searchTask(@RequestBody @Valid SearchTaskDto dto) {
        return service.searchTask(dto);
    }

    @Operation(summary = "Changing status of task")
    @PostMapping(value = "/changeStatus")
    public TaskCardDto changeTaskStatus(@RequestBody @Valid ChangeStatusOfTaskDto dto) {
        return service.changeTaskStatus(dto);
    }

    @PostMapping(value = "/getById")
    public TaskCardDto getTaskById(@RequestParam("taskId") Long taskId) {
        return service.getTaskById(taskId);
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleException(RuntimeException ex) {
        return ex.getMessage();
    }
}
