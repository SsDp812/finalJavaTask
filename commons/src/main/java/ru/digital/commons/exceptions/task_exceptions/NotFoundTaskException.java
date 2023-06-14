package ru.digital.commons.exceptions.task_exceptions;

public class NotFoundTaskException extends RuntimeException {
    public NotFoundTaskException() {
        super("Task was not found!");
    }
}
