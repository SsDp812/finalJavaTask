package org.digital.project_dto.team_dto.request_team_dto;

import lombok.Data;
import org.digital.roles.EmployeeProjectRole;

@Data
public class AddMemberToProjectDto {
    private Long memberAccountId;
    private EmployeeProjectRole role;
}
