package ru.digital.commons.exceptions.project_exceptions;

public class NullProjectDtoException extends RuntimeException {
    public NullProjectDtoException() {
        super("Null project dto!");
    }
}
