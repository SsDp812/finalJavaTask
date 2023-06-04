package org.digital.exceptions.project_exceptions;

public class NotUniqueProjectCodeNameException extends Exception{
    public NotUniqueProjectCodeNameException() {
        super("Not unique project code name!");
    }
}
