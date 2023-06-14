package ru.digital.commons.exceptions.project_exceptions;

public class NotFoundProjectException extends RuntimeException {
    public NotFoundProjectException() {
        super("Project was not found!");
    }
}
