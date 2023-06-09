package ru.digital.models.project_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digital.commons.enity_statuses.ProjectStatus;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Project")
public class Project implements Serializable {

    @Id
    @Column(name = "code_name", columnDefinition = "VARCHAR(255)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String projectCodeName;

    @Column(name = "project_name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String projectName;

    @Column(name = "project_description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @Column(name = "project_status", columnDefinition = "VARCHAR")
    @Enumerated(value = EnumType.STRING)
    private ProjectStatus projectStatus;

}
