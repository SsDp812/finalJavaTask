package org.digital.employee_dto.request_employee_dto.Filters;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.digital.enity_statuses.EmployeeStatus;
import org.digital.enity_statuses.TaskStatus;


@Data
@AllArgsConstructor
public class FilterForTaskAndExecutorByStatusesDto {
    private TaskStatus taskStatus;
    private EmployeeStatus executorStatus;
}
