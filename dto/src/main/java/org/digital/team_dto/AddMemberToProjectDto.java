package org.digital.team_dto;

import lombok.Data;

@Data
public class AddMemberToProjectDto {
    private Long memberAccountId;
    private String role;
}
