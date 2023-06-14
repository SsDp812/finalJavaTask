package ru.digital.commons.exceptions.member_exception;

public class NullMemberEmployeeException extends RuntimeException {
    public NullMemberEmployeeException() {
        super("Null member employee!");
    }
}
