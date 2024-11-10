package br.com.mateusulrich.recipeservice.common.exception;

public class DatabaseException extends DomainException{

    public DatabaseException(String message) {
        super(message, null);
    }
}
