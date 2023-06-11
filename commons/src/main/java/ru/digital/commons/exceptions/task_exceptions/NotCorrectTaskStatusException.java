package ru.digital.commons.exceptions.task_exceptions;

public class NotCorrectTaskStatusException extends Exception{
    public NotCorrectTaskStatusException() {
        super("Not correct task status!");
    }
}
