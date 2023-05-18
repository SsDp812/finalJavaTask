package org.digital.task_dto.request_task_dto;

import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;

@Data
public class SearchTaskDto {
    private String textFilter;
    private TaskStatus status;
    private Long employeeId; //member
    private Long authorId;
    private Timestamp startTime;
    private Timestamp endTime;
}
