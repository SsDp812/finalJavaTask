package org.digital.task_dto.request_task_dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchTaskDto {
    private String textFilter;
    private List<TaskStatus> statuses;
    private String executorId;
    private String authorId;
    private Date deadLineTimeStart;
    private Date deadLineTimeEnd;
    private Date startTaskTimeStart;
    private Date startTaskTimeEnd;
}
