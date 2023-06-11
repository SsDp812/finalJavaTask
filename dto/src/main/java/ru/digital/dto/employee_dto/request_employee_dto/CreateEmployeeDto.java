package ru.digital.dto.employee_dto.request_employee_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Employee for create")
public class CreateEmployeeDto {
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
