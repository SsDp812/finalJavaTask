package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Schema(description = "Employee for update")
public class UpdateEmployeeDto {
    @Schema(description = "Account id")
    @Min(value = 0,message = "id should be more than zero")
    @NotNull
    private Long accountId;
    @Schema(description = "Surname")
    @NotEmpty
    @Size(min = 3,max = 20,message = "surname should be between 3 and 20 characters")
    private String surname;
    @Schema(description = "Name")
    @NotEmpty
    @Size(min = 3,max = 20,message = "name should be between 3 and 20 characters")
    private String name;
    @Schema(description = "Middlename")
    private String middleName;
    @Schema(description = "Job title")
    private String jobTitle;
    @Schema(description = "Email address")
    @Size(min = 3,max = 30,message = "email should be between 3 and 20 characters")
    @Email
    private String email;
    @Schema(description = "Account login")
    @Size(min = 5,max = 30,message = "login should be between 5 and 20 characters")
    private String login;
    @Schema(description = "Account Password")
    @Size(min = 8,max = 30,message = "password should be between 8 and 20 characters")
    private String password;

}
