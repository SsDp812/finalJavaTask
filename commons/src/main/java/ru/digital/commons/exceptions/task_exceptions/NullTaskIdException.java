package ru.digital.commons.exceptions.task_exceptions;

public class NullTaskIdException extends RuntimeException {
    public NullTaskIdException() {
        super("Null task id!");
    }
}
