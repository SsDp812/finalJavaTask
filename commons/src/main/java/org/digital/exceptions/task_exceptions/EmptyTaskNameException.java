package org.digital.exceptions.task_exceptions;

public class EmptyTaskNameException extends Exception{
    public EmptyTaskNameException() {
        super("Empty task name exception!");
    }
}
