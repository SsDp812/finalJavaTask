package ru.digital.controllers.task_controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import ru.digital.business.task_services.Impls.TaskServiceImpl;
import ru.digital.business.task_services.TaskService;
import ru.digital.dto.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import ru.digital.dto.task_dto.request_task_dto.CreateTaskDto;
import ru.digital.dto.task_dto.request_task_dto.SearchTaskDto;
import ru.digital.dto.task_dto.request_task_dto.UpdateTaskDto;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/task")
@Tag(name = "TaskController", description = "Controller for tasks")
public class TaskController {
    private TaskService service;

    @Autowired
    public TaskController(TaskService service) {
        this.service = service;
    }

    @Operation(summary = "Creating new task")
    @PostMapping(value = "/new",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto createTask(@RequestBody @Valid CreateTaskDto dto) throws Exception {
        return service.createNewTask(dto);
    }

    @Operation(summary = "Updating task info")
    @PostMapping(value = "/update",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto changeTask(@RequestBody @Valid UpdateTaskDto dto) throws Exception {
        return service.changeTask(dto);
    }

    @Operation(summary = "Searching task by filter")
    @PostMapping(value = "/search",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskCardDto> searchTask(@RequestBody @Valid SearchTaskDto dto) throws Exception {
        return service.searchTask(dto);
    }

    @Operation(summary = "Changing status of task")
    @PostMapping(value = "/changeStatus",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public TaskCardDto changeTaskStatus(@RequestBody @Valid ChangeStatusOfTaskDto dto) throws Exception {
        return service.changeTaskStatus(dto);
    }
}
