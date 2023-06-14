package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeEmptyPasswordException extends RuntimeException {
    public EmployeeEmptyPasswordException() {
        super("Empty password!");
    }
}
