package org.digital.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Employee for update")
public class UpdateEmployeeDto {
    @Schema(description = "Account id")
    private Long accountId;
    @Schema(description = "Surname")
    private String surname;
    @Schema(description = "Name")
    private String name;
    @Schema(description = "Middlename")
    private String middleName;
    @Schema(description = "Job title")
    private String jobTitle;
    @Schema(description = "Email address")
    private String email;
    @Schema(description = "Account login")
    private String login;
    @Schema(description = "Account Password")
    private String password;
}
