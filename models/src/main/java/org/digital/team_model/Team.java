package org.digital.team_model;

import lombok.Data;
import org.digital.project_model.Project;
import org.digital.team_member_model.TeamMember;

import java.util.List;

@Data
public class Team {
    private Project project;
    private List<TeamMember> members;
}
