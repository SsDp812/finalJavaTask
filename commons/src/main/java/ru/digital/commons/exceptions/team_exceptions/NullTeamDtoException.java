package ru.digital.commons.exceptions.team_exceptions;

public class NullTeamDtoException extends RuntimeException {
    public NullTeamDtoException() {
        super("Null team dto!");
    }
}
