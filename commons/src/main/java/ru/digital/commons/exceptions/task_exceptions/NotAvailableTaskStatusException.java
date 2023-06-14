package ru.digital.commons.exceptions.task_exceptions;

public class NotAvailableTaskStatusException extends RuntimeException {
    public NotAvailableTaskStatusException() {
        super("Not available task status!");
    }
}
