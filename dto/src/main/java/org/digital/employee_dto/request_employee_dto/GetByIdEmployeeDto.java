package org.digital.employee_dto.request_employee_dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Employee for getting by id")
public class GetByIdEmployeeDto {
    @Schema(description = "Account id")
    private Long accountId;
}
