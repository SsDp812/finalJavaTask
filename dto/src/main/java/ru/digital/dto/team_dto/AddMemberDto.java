package ru.digital.dto.team_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.digital.commons.roles.EmployeeProjectRole;

@Data
@AllArgsConstructor
@Schema(description = "Member to adding in team")
public class AddMemberDto {
    @Schema(description = "Project code name")
    @NotEmpty
    @Size(min = 5,max = 25,message = "project codeMame length should be between 5 and 25 characters")
    private String projectCodeName;
    @Schema(description = "Employee account id")
    @NotNull
    @Min(value = 0,message = "accountId should be more than zero")
    private Long accountId;
    @Schema(description = "Employee role in team")
    private EmployeeProjectRole role;
}
