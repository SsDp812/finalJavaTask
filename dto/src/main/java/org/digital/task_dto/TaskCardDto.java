package org.digital.task_dto;

import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;

@Data
public class TaskCardDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private String executor;
    private Integer hours;
    private Timestamp deadLineTime;
    private String author;
    private Timestamp startTaskTime;
    private Timestamp editTaskTime;
    private TaskStatus taskStatus;

}
