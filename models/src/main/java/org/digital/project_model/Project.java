package org.digital.project_model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.enity_statuses.ProjectStatus;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Project")
public class Project {

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
