package org.digital.exceptions.employee_exceptions;

public class NotUniqieLoginException extends Exception{
    public NotUniqieLoginException() {
        super("Not unique login!");
    }
}
