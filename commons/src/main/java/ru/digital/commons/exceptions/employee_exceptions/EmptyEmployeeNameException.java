package ru.digital.commons.exceptions.employee_exceptions;

public class EmptyEmployeeNameException extends RuntimeException {
    public EmptyEmployeeNameException() {
        super("Empty name for employee!");
    }
}
