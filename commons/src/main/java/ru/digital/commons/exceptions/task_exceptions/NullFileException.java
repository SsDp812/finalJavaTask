package ru.digital.commons.exceptions.task_exceptions;

public class NullFileException extends Exception {
    public NullFileException() {
        super("Null task file");
    }
}
