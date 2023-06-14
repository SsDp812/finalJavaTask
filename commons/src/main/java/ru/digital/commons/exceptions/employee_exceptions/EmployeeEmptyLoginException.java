package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeEmptyLoginException extends RuntimeException {
    public EmployeeEmptyLoginException() {
        super("Empty login!");
    }
}
