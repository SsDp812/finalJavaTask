package org.digital.services.task_services;

import org.digital.task_dto.request_task_dto.ChangeStatusOfTaskDto;
import org.digital.task_dto.request_task_dto.CreateTaskDto;
import org.digital.task_dto.request_task_dto.SearchTaskDto;
import org.digital.task_dto.request_task_dto.UpdateTaskDto;
import org.digital.task_dto.response_task_dto.TaskCardDto;

import java.util.List;

public interface TaskService {
    public TaskCardDto createNewTask(CreateTaskDto dto) throws Exception;

    public TaskCardDto changeTask(UpdateTaskDto dto) throws Exception;

    public List<TaskCardDto> searchTask(SearchTaskDto dto) throws Exception;

    public TaskCardDto changeTaskStatus(ChangeStatusOfTaskDto dto) throws Exception;

}
