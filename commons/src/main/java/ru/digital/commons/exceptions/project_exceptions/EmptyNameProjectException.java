package ru.digital.commons.exceptions.project_exceptions;

public class EmptyNameProjectException extends Exception{
    public EmptyNameProjectException() {
        super("Empty project name!");
    }
}
