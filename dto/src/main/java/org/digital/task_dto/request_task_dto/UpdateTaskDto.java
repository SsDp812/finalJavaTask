package org.digital.task_dto.request_task_dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UpdateTaskDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private Long executorId;
    private Integer hours;
    private Timestamp deadLineTime;
    private Timestamp startTaskTime;
    private Timestamp editTaskTime;
}