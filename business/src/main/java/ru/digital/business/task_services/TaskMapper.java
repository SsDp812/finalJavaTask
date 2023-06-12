package ru.digital.business.task_services;

import ru.digital.business.employee_services.EmployeeMapper;
import ru.digital.dto.task_dto.response_task_dto.TaskCardDto;
import ru.digital.models.task_model.Task;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        if(task.getParentTask() != null){
            tempDto.setParentTask(getTaskCardDto(task.getParentTask()));
        }
        if(task.getChildTasks() != null && !task.getChildTasks().isEmpty()){
            List<Long> childs = new ArrayList<>();
            for(var ch : task.getChildTasks()){
                childs.add(ch.getTaskId());
            }
            tempDto.setChildTasksIds(childs);
        }
        if(task.getFileName() != null && !Objects.equals(task.getFileName(),"")){
            tempDto.setFile(new File(task.getFileName()));
        }
        return tempDto;
    }
}
