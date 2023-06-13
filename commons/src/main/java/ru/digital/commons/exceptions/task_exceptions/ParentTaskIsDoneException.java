package ru.digital.commons.exceptions.task_exceptions;

public class ParentTaskIsDoneException extends Exception {
    public ParentTaskIsDoneException() {
        super("Parent task is done or closed!");
    }
}
