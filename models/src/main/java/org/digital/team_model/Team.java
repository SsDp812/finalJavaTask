package org.digital.team_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.project_model.Project;
import org.digital.team_member_model.TeamMember;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teamid")
    private Long teamId;

    @JoinColumn(name = "project_code_name",referencedColumnName = "code_name")
    @OneToOne()
    private Project project;

    @OneToMany(mappedBy = "team")
    private List<TeamMember> members;

}
