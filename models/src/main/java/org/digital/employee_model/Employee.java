package org.digital.employee_model;


import lombok.AllArgsConstructor;
import lombok.Data;

import org.digital.enity_statuses.EmployeeStatus;

@Data
@AllArgsConstructor
public class Employee {
    private Long accountId;
    private String surname;
    private String name;
    private String middleName; //отчество
    private String jobTitle;
    private String login;
    private String password;
    private String email;
    private EmployeeStatus employeeStatus;
}
