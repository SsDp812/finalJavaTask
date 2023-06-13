package ru.digital.dto.employee_dto.request_employee_dto.Filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.digital.commons.enity_statuses.EmployeeStatus;
import ru.digital.commons.enity_statuses.TaskStatus;


@Data
@AllArgsConstructor
public class FilterForTaskAndExecutorByStatusesDto {
    private TaskStatus taskStatus;
    private EmployeeStatus executorStatus;
}
