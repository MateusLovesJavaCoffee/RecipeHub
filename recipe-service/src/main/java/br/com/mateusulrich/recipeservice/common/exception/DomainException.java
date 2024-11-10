package br.com.mateusulrich.recipeservice.common.exception;

public class DomainException extends RuntimeException {
    public DomainException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}
