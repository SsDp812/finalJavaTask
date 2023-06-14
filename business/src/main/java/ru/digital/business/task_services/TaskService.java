package ru.digital.business.task_services;

import org.springframework.web.multipart.MultipartFile;
import ru.digital.dto.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import ru.digital.dto.task_dto.request_task_dto.CreateTaskDto;
import ru.digital.dto.task_dto.request_task_dto.SearchTaskDto;
import ru.digital.dto.task_dto.request_task_dto.UpdateTaskDto;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;

import java.util.List;

public interface TaskService {
    public TaskCardDto createNewTask(CreateTaskDto dto);

    public TaskCardDto changeTask(UpdateTaskDto dto);

    public List<TaskCardDto> searchTask(SearchTaskDto dto);

    public TaskCardDto changeTaskStatus(ChangeStatusOfTaskDto dto);

    public TaskCardDto getTaskById(Long id);

    public void loadFileToTask(MultipartFile file, Long taskId);
}
