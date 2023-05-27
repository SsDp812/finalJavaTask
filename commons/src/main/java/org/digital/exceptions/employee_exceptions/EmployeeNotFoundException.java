package org.digital.exceptions.employee_exceptions;

public class EmployeeNotFoundException extends Exception{
    public EmployeeNotFoundException() {
        super("Employee was not found!");
    }
}
