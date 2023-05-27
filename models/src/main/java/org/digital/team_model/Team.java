package org.digital.team_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.project_model.Project;
import org.digital.team_member_model.TeamMember;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "project_code_name")
    private Project project;

    @OneToMany
    @JoinColumn(name = "member_id",referencedColumnName = "member_id")
    private List<TeamMember> members;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;
}
