package org.digital.team_dto;

import lombok.Data;

@Data
public class AddMemberDto {
    private String projectCodeName;
    private Long accountId;
    private String role;
}
