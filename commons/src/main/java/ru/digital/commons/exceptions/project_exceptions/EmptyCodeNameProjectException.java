package ru.digital.commons.exceptions.project_exceptions;

public class EmptyCodeNameProjectException extends RuntimeException {
    public EmptyCodeNameProjectException() {
        super("Empty project code name!");
    }
}
