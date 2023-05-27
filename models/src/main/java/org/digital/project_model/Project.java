package org.digital.project_model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.enity_statuses.ProjectStatus;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.Type;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Id
    @Column(name = "code_name")
    private String projectCodeName;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String description;

    @Column(name = "project_status")
    private ProjectStatus projectStatus;
}
