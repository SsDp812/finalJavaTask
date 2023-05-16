package org.digital.task_dto;

import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;

@Data
public class CreateTaskDto {
    private String taskName;
    private String taskDescription;
    private Long executorID;
    private Integer hours;
    private Timestamp deadLineTime;
    private Long authorID;
    private Timestamp startTaskTime;
    private Timestamp editTaskTime;
}
