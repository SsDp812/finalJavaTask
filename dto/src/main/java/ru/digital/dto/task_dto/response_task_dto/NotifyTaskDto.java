package ru.digital.dto.task_dto.response_task_dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digital.commons.enity_statuses.NotifyStatus;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NotifyTaskDto implements Serializable {
    private NotifyStatus status;
    private Long taskId;
    private String email;
    private String employeeName;
    private String employeeSurname;
    private String taskName;
    private String taskDescription;
    private Date deadline;
}
