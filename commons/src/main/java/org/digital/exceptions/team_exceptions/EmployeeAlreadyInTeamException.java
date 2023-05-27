package org.digital.exceptions.team_exceptions;

public class EmployeeAlreadyInTeamException extends Exception{
    public EmployeeAlreadyInTeamException() {
        super("Employee already in team!");
    }
}
