package ru.digital.commons.exceptions.task_exceptions;

public class ParentTaskIsDoneException extends RuntimeException {
    public ParentTaskIsDoneException() {
        super("Parent task is done or closed!");
    }
}
