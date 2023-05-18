package org.digital.project_dto.team_dto.response_team_dto;

import lombok.Data;
import org.digital.member_dto.response_member_dto.MemberCardDto;

import java.util.List;

@Data
public class TeamCardDto {
    private String projectCodeName;
    private List<MemberCardDto> members;
}
