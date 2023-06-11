package ru.digital.commons.exceptions.project_exceptions;

public class NotFoundProjectException extends Exception{
    public NotFoundProjectException() {
        super("Project was not found!");
    }
}
