package ru.digital.business.task_services;

import ru.digital.business.employee_services.EmployeeMapper;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import ru.digital.models.task_model.Task;

public class TaskMapper {
    //Mapper for mapping task to TaskCardDto
    public static TaskCardDto getTaskCardDto(Task task) {
        TaskCardDto tempDto = new TaskCardDto();
        tempDto.setTaskId(task.getTaskId());
        tempDto.setTaskName(task.getTaskName());
        tempDto.setTaskDescription(task.getTaskDescription());
        tempDto.setHours(task.getHours());
        tempDto.setExecutor(EmployeeMapper.getEmployeeDtoCard(task.getExecutor()));
        tempDto.setAuthor(EmployeeMapper.getEmployeeDtoCard(task.getAuthor()));
        tempDto.setTaskStatus(task.getTaskStatus());
        tempDto.setStartTaskTime(task.getStartTaskTime());
        tempDto.setDeadLineTime(task.getDeadLineTime());
        tempDto.setEditTaskTime(task.getEditTaskTime());
        return tempDto;
    }
}
