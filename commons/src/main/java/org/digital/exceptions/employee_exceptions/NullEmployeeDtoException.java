package org.digital.exceptions.employee_exceptions;

public class NullEmployeeDtoException extends Exception{
    public NullEmployeeDtoException() {
        super("Employee dto is null!");
    }
}
