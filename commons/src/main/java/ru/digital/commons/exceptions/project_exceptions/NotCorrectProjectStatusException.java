package ru.digital.commons.exceptions.project_exceptions;

public class NotCorrectProjectStatusException extends RuntimeException {
    public NotCorrectProjectStatusException() {
        super("Not correct project status!");
    }
}
