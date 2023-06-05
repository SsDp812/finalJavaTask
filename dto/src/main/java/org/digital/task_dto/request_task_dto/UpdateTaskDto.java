package org.digital.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Task for update")
public class UpdateTaskDto {
    @Schema(description = "Task id")
    private Long taskId;
    @Schema(description = "Task name")
    private String taskName;
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "Task description")
    private String taskDescription;
    @Schema(description = "Executor id")
    private Long executorId;
    @Schema(description = "Hours time for task")
    private Integer hours;
    @Schema(description = "End time for task")
    private Date deadLineTime;
}
