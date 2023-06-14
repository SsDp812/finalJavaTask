package ru.digital.commons.exceptions.task_exceptions;

public class NullTaskDtoException extends RuntimeException {
    public NullTaskDtoException() {
        super("Null task dto!");
    }
}
