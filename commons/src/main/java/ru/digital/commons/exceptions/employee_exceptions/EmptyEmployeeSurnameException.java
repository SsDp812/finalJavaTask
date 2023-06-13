package ru.digital.commons.exceptions.employee_exceptions;

public class EmptyEmployeeSurnameException extends Exception{
    public EmptyEmployeeSurnameException() {
        super("Empty employee surname!");
    }
}
