package org.digital.exceptions.project_exceptions;

public class EmptyCodeNameProjectException extends Exception{
    public EmptyCodeNameProjectException() {
        super("Empty project code name!");
    }
}
