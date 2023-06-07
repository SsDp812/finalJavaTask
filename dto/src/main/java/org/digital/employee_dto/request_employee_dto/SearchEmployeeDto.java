package org.digital.employee_dto.request_employee_dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Search employee by textFilter")
public class SearchEmployeeDto {
    @Schema(description = "Text for search")
    private String searchFilter;
}
