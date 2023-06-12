package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Task for get by id")
public class GetTaskByIdDto {
    @Schema(description = "Task id")
    @Min(value = 0,message = "task id should be more than zero")
    @NotNull
    private Long taskId;
}
