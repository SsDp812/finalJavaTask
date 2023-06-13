package ru.digital.commons.exceptions.task_exceptions;

public class NullTaskIdException extends Exception {
    public NullTaskIdException() {
        super("Null task id!");
    }
}
