package ru.digital.commons.exceptions.employee_exceptions;

public class EmptyEmployeeSurnameException extends RuntimeException {
    public EmptyEmployeeSurnameException() {
        super("Empty employee surname!");
    }
}
