package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Task for get by id")
public class GetTaskByIdDto {
    @Schema(description = "Task id")
    private Long taskId;
}
