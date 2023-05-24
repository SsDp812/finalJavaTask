package org.digital.employee_dto.request_employee_dto;


import lombok.Data;

import java.lang.ref.PhantomReference;

@Data
public class GetEmployeeByLoginAndPassword {
    private String login;
    private String password;
}
