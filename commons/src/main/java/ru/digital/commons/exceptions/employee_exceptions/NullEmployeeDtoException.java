package ru.digital.commons.exceptions.employee_exceptions;

public class NullEmployeeDtoException extends Exception{
    public NullEmployeeDtoException() {
        super("Employee dto is null!");
    }
}
