package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.lang.ref.PhantomReference;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Schema(description = "Employee for getting by login and password")
public class GetEmployeeByLoginAndPassword {
    @Schema(description = "Account login")
    private String login;
    @Schema(description = "Account password")
    private String password;
}
