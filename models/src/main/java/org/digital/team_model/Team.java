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
    @Column(name = "project_code_name",columnDefinition = "VARCHAR(255)")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Project project;

    @OneToMany
    @JoinColumn(name = "member_id",referencedColumnName = "member_id")
    private List<TeamMember> members;

    @Column(name = "member_id", insertable = false, updatable = false)
    private Long memberId;
}
