package org.digital.task_model;

import lombok.Data;
import org.digital.employee_model.Employee;
import org.digital.project_model.Project;

import java.sql.Timestamp;
import java.util.Date;


@Data
public class Task {
    private Long taskId;
    private Project project;
    private String taskName;
    private String taskDescription;
    private Employee executor;
    private Integer hours;
    private Date deadLineTime;
    private Employee author;
    private Date startTaskTime;
    private Date editTaskTime;
    private Date taskStatus;

}
