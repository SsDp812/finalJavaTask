package ru.digital.commons.exceptions.employee_exceptions;

public class EmployeeEmptyPasswordException extends Exception{
    public EmployeeEmptyPasswordException() {
        super("Empty password!");
    }
}
