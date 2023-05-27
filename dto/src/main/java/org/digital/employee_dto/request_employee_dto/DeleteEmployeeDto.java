package org.digital.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Employee for delete")
public class DeleteEmployeeDto {
    @Schema(description = "Account id")
    private Long accountId;
}
