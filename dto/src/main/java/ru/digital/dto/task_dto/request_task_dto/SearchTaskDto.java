package ru.digital.dto.task_dto.request_task_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digital.commons.enity_statuses.TaskStatus;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Task for searching")
public class SearchTaskDto {
    @Schema(description = "Text filter for searching")
    private String textFilter;
    @Schema(description = "Filter - task statuses")
    private List<TaskStatus> statuses;
    @Schema(description = "Executor id")
    @Min(value = 0, message = "Executor id should be more tan zero")
    private Long executorId;
    @Schema(description = "Author id")
    @Min(value = 0, message = "Executor id should be more tan zero")
    private Long authorId;
    @Schema(description = "End time start for task")
    private Date deadLineTimeStart;
    @Schema(description = "End time end for task")
    private Date deadLineTimeEnd;
    @Schema(description = "Start time start for task")
    private Date startTaskTimeStart;
    @Schema(description = "Start time end for task")
    private Date startTaskTimeEnd;
}
