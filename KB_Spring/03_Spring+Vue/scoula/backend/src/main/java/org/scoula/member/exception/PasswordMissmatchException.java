package org.scoula.member.exception;

public class PasswordMissmatchException extends RuntimeException {
    public PasswordMissmatchException(String message) {
        super(message);
    }
}
