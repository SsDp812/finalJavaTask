package org.digital.project_model;


import lombok.Data;
import org.digital.enity_statuses.ProjectStatus;

@Data
public class Project {
    private String projectCodeName;
    private String projectName;
    private String description;
    private ProjectStatus projectStatus;
}
