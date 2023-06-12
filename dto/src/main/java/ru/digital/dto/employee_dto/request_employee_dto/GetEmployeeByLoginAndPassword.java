package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Employee for getting by login and password")
public class GetEmployeeByLoginAndPassword {
    @Schema(description = "Account login")
    @Size(min = 5, max = 30, message = "login should be between 5 and 20 characters")
    private String login;
    @Schema(description = "Account password")
    @Size(min = 8, max = 30, message = "password should be between 8 and 20 characters")
    private String password;
}
