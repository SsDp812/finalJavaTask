package ru.digital.commons.exceptions.task_exceptions;

public class NotCorrectTaskStatusException extends RuntimeException {
    public NotCorrectTaskStatusException() {
        super("Not correct task status!");
    }
}
