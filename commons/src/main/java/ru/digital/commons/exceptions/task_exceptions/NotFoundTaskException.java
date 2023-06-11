package ru.digital.commons.exceptions.task_exceptions;

public class NotFoundTaskException extends Exception{
    public NotFoundTaskException() {
        super("Task was not found!");
    }
}
