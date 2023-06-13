package ru.digital.commons.exceptions.employee_exceptions;

public class EmptyEmployeeNameException extends Exception{
    public EmptyEmployeeNameException() {
        super("Empty name for employee!");
    }
}
