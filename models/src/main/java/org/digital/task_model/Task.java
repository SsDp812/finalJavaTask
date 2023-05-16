package org.digital.task_model;

import lombok.Data;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.TaskStatus;

import java.sql.Time;
import java.sql.Timestamp;

@Data
public class Task {
    private Long taskId;
    private String taskName;
    private String taskDescription;
    private Employee executor;
    private Integer hours;
    private Timestamp deadLineTime;
    private Employee author;
    private Timestamp startTaskTime;
    private Timestamp editTaskTime;
    private TaskStatus taskStatus;

}
