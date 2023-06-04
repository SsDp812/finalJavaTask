package org.digital.team_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Member to adding in team")
public class AddMemberDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "Employee account id")
    private Long accountId;
    @Schema(description = "Employee role in team")
    private String role;
}
