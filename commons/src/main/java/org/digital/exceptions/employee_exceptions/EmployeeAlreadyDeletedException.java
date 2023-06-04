package org.digital.exceptions.employee_exceptions;

public class EmployeeAlreadyDeletedException extends Exception{
    public EmployeeAlreadyDeletedException() {
        super("Employee was deleted!");
    }
}
