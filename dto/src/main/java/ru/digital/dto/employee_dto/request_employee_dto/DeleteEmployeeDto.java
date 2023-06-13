package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Employee for delete")
public class DeleteEmployeeDto {
    @Schema(description = "Account id")
    @NotNull
    @Min(value = 0, message = "id should be more than zero")
    private Long accountId;
}
