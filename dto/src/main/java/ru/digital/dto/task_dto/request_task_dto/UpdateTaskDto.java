package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Task for update")
public class UpdateTaskDto {
    @Schema(description = "Task id")
    @Min(value = 0,message = "task id should be more than zero")
    @NotNull
    private Long taskId;
    @Schema(description = "Task name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "task name length should be between 5 and 25 characters")
    private String taskName;
    @Schema(description = "Project code name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "project codeMame length should be between 5 and 25 characters")
    private String projectCodeName;
    @Schema(description = "Task description")
    private String taskDescription;
    @Schema(description = "Executor id")
    @NotNull
    @Min(value = 0,message = "id should be more than zero")
    private Long executorId;
    @Schema(description = "Hours time for task")
    @Min(value = 1,message = "hours should be more than zero")
    private Integer hours;
    @Future
    @Schema(description = "End time for task")
    private Date deadLineTime;
    @Schema(description = "Parent task")
    @Min(value = 0,message = "parent task id should be more than zero")
    private Long parentTaskId;
}
