package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException() {
        super("Employee was not found!");
    }
}
