package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeAlreadyDeletedException extends RuntimeException {
    public EmployeeAlreadyDeletedException() {
        super("Employee was deleted!");
    }
}
