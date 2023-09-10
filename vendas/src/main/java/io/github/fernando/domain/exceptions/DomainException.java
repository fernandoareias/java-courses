package io.github.fernando.domain.exceptions;

public class DomainException extends  RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}
