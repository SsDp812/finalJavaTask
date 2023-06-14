package ru.digital.commons.exceptions.task_exceptions;

public class NullFileException extends RuntimeException {
    public NullFileException() {
        super("Null task file");
    }
}
