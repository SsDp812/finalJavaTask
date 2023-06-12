package ru.digital.controllers.task_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.digital.business.task_services.Impls.TaskServiceImpl;
import ru.digital.business.task_services.TaskService;
import ru.digital.dto.task_dto.request_task_dto.*;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

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
    @PostMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto createTask(@RequestBody @Valid CreateTaskDto dto) throws Exception {
        return service.createNewTask(dto);
    }

    @PostMapping(value = "/addFile")
    public void add(@RequestParam("file") MultipartFile file, @RequestParam("taskId") Long taskId) throws Exception {
       service.loadFileToTask(file,taskId);
    }

    @Operation(summary = "Updating task info")
    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto changeTask(@RequestBody @Valid UpdateTaskDto dto) throws Exception {
        return service.changeTask(dto);
    }

    @Operation(summary = "Searching task by filter")
    @PostMapping(value = "/search", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskCardDto> searchTask(@RequestBody @Valid SearchTaskDto dto) throws Exception {
        return service.searchTask(dto);
    }

    @Operation(summary = "Changing status of task")
    @PostMapping(value = "/changeStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto changeTaskStatus(@RequestBody @Valid ChangeStatusOfTaskDto dto) throws Exception {
        return service.changeTaskStatus(dto);
    }

    @PostMapping(value = "/getById")
    public TaskCardDto getTaskById(@RequestParam("taskId") Long taskId) throws Exception {
        return service.getTaskById(taskId);
    }
}
