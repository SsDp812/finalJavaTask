package ru.digital.dto.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Search employee by textFilter")
public class SearchEmployeeDto {
    @Schema(description = "Text for search")
    @Size(max = 20,message = "max search filter length = 20")
    private String searchFilter;
}
