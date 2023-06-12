package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Schema(description = "Task for create")
public class CreateTaskDto {
    @Schema(description = "Task name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "task name length should be between 5 and 25 characters")
    private String taskName;
    @Schema(description = "Task description")
    private String taskDescription;
    @Schema(description = "Project code name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "project codeMame length should be between 5 and 25 characters")
    private String projectCodeName;
    @Schema(description = "Executor id")
    @NotNull
    @Min(value = 0,message = "id should be more than zero")
    private Long executorId;
    @Schema(description = "Hours time for task")
    @Min(value = 1,message = "hours should be more than zero")
    private Integer hours;
    @Schema(description = "End time for task")
    @Future
    private Date deadLineTime;
}
