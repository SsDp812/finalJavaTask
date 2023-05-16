package org.digital.team_dto;

import lombok.Data;

import java.util.List;

@Data
public class ViewTeamDto {
    private String projectCodeName;
    private List<String> members;
}
