package org.digital.exceptions.employee_exceptions;

public class EmployeeEmptyLoginException extends Exception{
    public EmployeeEmptyLoginException() {
        super("Empty login!");
    }
}
