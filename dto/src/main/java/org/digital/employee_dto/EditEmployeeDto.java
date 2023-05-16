package org.digital.employee_dto;


import lombok.Data;

@Data
public class EditEmployeeDto {
    private Long accountId;


    private String surname;
    private String name;
    private String middleName; //отчество
    private String jobTitle;
    private String email;

    private String login;
    private String password;
}
