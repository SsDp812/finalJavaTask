package ru.digital.dto.employee_dto.request_employee_dto.Filters;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardTaskAndExecutorDto {
    private String executorName;
    private String executorEmail;

    private String taskName;
    private String taskDescription;

}
