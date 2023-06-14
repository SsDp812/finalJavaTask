package ru.digital.commons.exceptions.employee_exceptions;

public class NullEmployeeDtoException extends RuntimeException {
    public NullEmployeeDtoException() {
        super("Employee dto is null!");
    }
}
