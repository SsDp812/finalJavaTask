package ru.digital.commons.exceptions.task_exceptions;

public class ChildTaskNotDoneException extends RuntimeException {
    public ChildTaskNotDoneException() {
        super("Child tasks are open, you cant close this task!");
    }
}
