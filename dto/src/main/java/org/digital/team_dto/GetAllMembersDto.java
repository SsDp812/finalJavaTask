package org.digital.team_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Getting all members of team")
public class GetAllMembersDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
}
