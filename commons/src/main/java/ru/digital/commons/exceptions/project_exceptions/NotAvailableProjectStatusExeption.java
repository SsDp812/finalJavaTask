package ru.digital.commons.exceptions.project_exceptions;

public class NotAvailableProjectStatusExeption extends RuntimeException {
    public NotAvailableProjectStatusExeption() {
        super("Not available status!");
    }
}
