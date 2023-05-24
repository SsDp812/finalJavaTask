package org.digital.employee_dto.response_employee_dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeCardDto {
    private String surname;
    private String name;
    private String middleName; //отчество
    private String jobTitle;
    private String email;
    private String status;
}
