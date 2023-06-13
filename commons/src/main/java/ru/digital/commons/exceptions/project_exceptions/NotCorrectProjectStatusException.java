package ru.digital.commons.exceptions.project_exceptions;

public class NotCorrectProjectStatusException extends Exception{
    public NotCorrectProjectStatusException() {
        super("Not correct project status!");
    }
}
