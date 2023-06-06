package org.digital.services.task_services;

import org.digital.services.employee_services.EmployeeMapper;
import org.digital.task_dto.response_task_dto.TaskCardDto;
import org.digital.task_model.Task;

public class TaskMapper {
    public static TaskCardDto getTaskCardDto(Task task){
        TaskCardDto tempDto = new TaskCardDto();
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
