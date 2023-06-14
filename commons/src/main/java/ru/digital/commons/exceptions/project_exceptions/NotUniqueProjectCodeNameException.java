package ru.digital.commons.exceptions.project_exceptions;

public class NotUniqueProjectCodeNameException extends RuntimeException {
    public NotUniqueProjectCodeNameException() {
        super("Not unique project code name!");
    }
}
