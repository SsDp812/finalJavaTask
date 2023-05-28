package org.digital.team_member_model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.digital.employee_model.Employee;
import org.digital.roles.EmployeeProjectRole;
import org.hibernate.annotations.JdbcType;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teammember")
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "employee_id",referencedColumnName = "accountid")
    private Employee member;
    @Column(name = "member_role")
    @Enumerated(value = EnumType.STRING)
    private EmployeeProjectRole role;
}
