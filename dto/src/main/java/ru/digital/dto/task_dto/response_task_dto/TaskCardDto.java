package ru.digital.dto.task_dto.response_task_dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import ru.digital.dto.employee_dto.response_employee_dto.EmployeeCardDto;
import ru.digital.commons.enity_statuses.TaskStatus;

import java.io.File;
import java.util.Date;
import java.util.List;


@Data
public class TaskCardDto {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private EmployeeCardDto executor;
    private Integer hours;
    private Date deadLineTime;
    private EmployeeCardDto author;
    private Date startTaskTime;
    private Date editTaskTime;
    private TaskStatus taskStatus;
    private File file;
    private TaskCardDto parentTask;
    private List<Long> childTasksIds;

}
