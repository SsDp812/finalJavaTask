package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Task for create")
public class CreateTaskDto {
    @Schema(description = "Task name")
    private String taskName;
    @Schema(description = "Task description")
    private String taskDescription;
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "Executor id")
    private Long executorId;
    @Schema(description = "Hours time for task")
    private Integer hours;
    @Schema(description = "End time for task")
    private Date deadLineTime;
    @Schema(description = "Start time for task")
    private Date startTaskTime;

}
