package ru.digital.commons.exceptions.task_exceptions;

public class NotAvailableTaskStatusException extends Exception{
    public NotAvailableTaskStatusException() {
        super("Not available task status!");
    }
}
