package org.digital.exceptions.task_exceptions;

public class TooLessTimeTaskException extends Exception{
    public TooLessTimeTaskException() {
        super("Too less time for this task!");
    }
}
