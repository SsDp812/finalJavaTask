package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Employee for delete")
public class DeleteEmployeeDto {
    @Schema(description = "Account id")
    private Long accountId;
}
