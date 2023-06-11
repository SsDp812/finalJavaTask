package ru.digital.dto.task_dto.request_task_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Task for changing status")
public class ChangeStatusOfTaskDto {
    @Schema(description = "Task id")
    private Long taskId;
    @Schema(description = "Task status")
    private String taskStatus;
}
