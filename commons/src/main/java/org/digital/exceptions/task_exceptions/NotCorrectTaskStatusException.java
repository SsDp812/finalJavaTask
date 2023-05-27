package org.digital.exceptions.task_exceptions;

public class NotCorrectTaskStatusException extends Exception{
    public NotCorrectTaskStatusException() {
        super("Not correct task status!");
    }
}
