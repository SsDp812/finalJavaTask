package org.digital.employee_dto;


import lombok.Data;

@Data
public class EmployeeCardDto {
    private String surname;
    private String name;
    private String middleName; //отчество
    private String jobTitle;
    private String email;
    private String status;
    private String login;
    private String password;
}
