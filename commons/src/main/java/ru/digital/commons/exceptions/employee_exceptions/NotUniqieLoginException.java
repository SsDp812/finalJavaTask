package ru.digital.commons.exceptions.employee_exceptions;

public class NotUniqieLoginException extends Exception{
    public NotUniqieLoginException() {
        super("Not unique login!");
    }
}
