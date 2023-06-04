package org.digital.exceptions.project_exceptions;

public class EmptyNameProjectException extends Exception{
    public EmptyNameProjectException() {
        super("Empty project name!");
    }
}
