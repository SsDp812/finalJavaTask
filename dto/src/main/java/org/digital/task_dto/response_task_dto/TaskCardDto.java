package org.digital.task_dto.response_task_dto;

import lombok.Data;
import org.digital.employee_dto.response_employee_dto.EmployeeCardDto;
import org.digital.enity_statuses.TaskStatus;

import java.util.Date;


@Data
public class TaskCardDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private EmployeeCardDto executor;
    private Integer hours;
    private Date deadLineTime;
    private EmployeeCardDto author;
    private Date startTaskTime;
    private Date editTaskTime;
    private TaskStatus taskStatus;

}
