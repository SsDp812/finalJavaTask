package ru.digital.models.team_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.digital.models.project_model.Project;
import ru.digital.models.team_member_model.TeamMember;


import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Team")
public class Team implements Serializable {

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
