package org.digital.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;
import java.util.Date;

@Data
@Schema(description = "Task for create")
public class CreateTaskDto {
    @Schema(description = "Task name")
    private String taskName;
    @Schema(description = "Task description")
    private String taskDescription;
    @Schema(description = "Executor id")
    private Long executorId;
    @Schema(description = "Hours time for task")
    private Integer hours;
    @Schema(description = "End time for task")
    private Date deadLineTime;
    @Schema(description = "Start time for task")
    private Date startTaskTime;
    @Schema(description = "Edit time for task")
    private Date editTaskTime;
}
