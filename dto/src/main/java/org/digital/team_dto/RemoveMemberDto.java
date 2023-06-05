package org.digital.team_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Member for removing from team")
public class RemoveMemberDto {
    @Schema(description = "Project code name")
    private String projectCodeName;
    @Schema(description = "Employee account id")
    private Long accountId;
}
