package org.digital.task_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.employee_model.Employee;
import org.digital.enity_statuses.TaskStatus;
import org.digital.project_model.Project;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;


    @ManyToOne
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @JoinColumn(name = "project_code_name", referencedColumnName = "code_name", columnDefinition = "VARCHAR(255)")
    private Project project;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_description")
    private String taskDescription;

    @ManyToOne
    @JoinColumn(name = "task_executor", referencedColumnName = "accountid")
    private Employee executor;

    @Column(name = "task_hours_time")
    private Integer hours;

    @Column(name = "end_time")
    private Date deadLineTime;

    @ManyToOne
    @JoinColumn(name = "task_author", referencedColumnName = "accountid")
    private Employee author;

    @Column(name = "start_time")
    private Date startTaskTime;

    @Column(name = "edit_time")
    private Date editTaskTime;

    @Column(name = "task_status")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;

}
