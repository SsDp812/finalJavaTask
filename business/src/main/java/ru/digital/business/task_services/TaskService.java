package ru.digital.business.task_services;

import org.springframework.web.multipart.MultipartFile;
import ru.digital.commons.exceptions.task_exceptions.NotFoundTaskException;
import ru.digital.dto.task_dto.request_task_dto.*;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import ru.digital.models.task_model.Task;

import java.util.List;

public interface TaskService {
    public TaskCardDto createNewTask(CreateTaskDto dto) throws Exception;

    public TaskCardDto changeTask(UpdateTaskDto dto) throws Exception;

    public List<TaskCardDto> searchTask(SearchTaskDto dto) throws Exception;

    public TaskCardDto changeTaskStatus(ChangeStatusOfTaskDto dto) throws Exception;
    public TaskCardDto getTaskById(Long id) throws NotFoundTaskException;
    public void loadFileToTask(MultipartFile file, Long taskId) throws Exception;
}
