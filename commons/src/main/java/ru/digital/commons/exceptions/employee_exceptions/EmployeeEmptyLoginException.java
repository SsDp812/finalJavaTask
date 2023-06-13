package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeEmptyLoginException extends Exception{
    public EmployeeEmptyLoginException() {
        super("Empty login!");
    }
}
