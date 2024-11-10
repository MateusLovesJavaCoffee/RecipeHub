package br.com.mateusulrich.recipeservice.common.exception;

public class MissingIdentifiersException extends DomainException{

    public MissingIdentifiersException(String message) {
        super(message, null);
    }
}
