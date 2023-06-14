package ru.digital.dto.task_dto.request_task_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.digital.commons.enity_statuses.TaskStatus;

@Data
@AllArgsConstructor
@Schema(description = "Task for changing status")
public class ChangeStatusOfTaskDto {
    @Schema(description = "Task id")
    @Min(value = 0, message = "task id should be more than zero")
    @NotNull
    private Long taskId;
    @Schema(description = "Task status")
    private TaskStatus taskStatus;
}
