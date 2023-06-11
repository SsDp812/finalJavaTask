package ru.digital.commons.exceptions.task_exceptions;

public class NullTaskDtoException extends Exception{
    public NullTaskDtoException() {
        super("Null task dto!");
    }
}
