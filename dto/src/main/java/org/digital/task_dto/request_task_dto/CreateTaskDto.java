package org.digital.task_dto.request_task_dto;

import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class CreateTaskDto {
    private String taskName;
    private String taskDescription;
    private Long executorId;
    private Integer hours;
    private Date deadLineTime;
    private Date startTaskTime;
    private Date editTaskTime;
}
