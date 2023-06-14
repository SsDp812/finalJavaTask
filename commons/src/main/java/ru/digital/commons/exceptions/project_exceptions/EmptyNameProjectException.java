package ru.digital.commons.exceptions.project_exceptions;

public class EmptyNameProjectException extends RuntimeException {
    public EmptyNameProjectException() {
        super("Empty project name!");
    }
}
