package ru.digital.dto.team_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Getting all members of team")
public class GetAllMembersDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
}
