package org.digital.task_dto;

import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;

@Data
public class EditTaskDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private Long executorId;
    private Integer hours;
    private Timestamp deadLineTime;
    private Long authorId;
    private Timestamp startTaskTime;
    private Timestamp editTaskTime;
}
