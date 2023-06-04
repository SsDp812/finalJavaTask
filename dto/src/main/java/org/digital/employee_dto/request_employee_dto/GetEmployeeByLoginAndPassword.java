package org.digital.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
@Schema(description = "Employee for getting by login and password")
public class GetEmployeeByLoginAndPassword {
    @Schema(description = "Account login")
    private String login;
    @Schema(description = "Account password")
    private String password;
}
