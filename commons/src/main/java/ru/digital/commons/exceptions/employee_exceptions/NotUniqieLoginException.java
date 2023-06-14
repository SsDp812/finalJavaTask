package ru.digital.commons.exceptions.employee_exceptions;

public class NotUniqieLoginException extends RuntimeException {
    public NotUniqieLoginException() {
        super("Not unique login!");
    }
}
