package org.digital.exceptions.project_exceptions;

public class NotFoundProjectException extends Exception{
    public NotFoundProjectException() {
        super("Project was not found!");
    }
}
