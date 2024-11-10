package br.com.mateusulrich.recipeservice.common.exception;

public class EntityInUseException extends DomainException{
    public EntityInUseException(String message) {
        super(message, null);
    }
}
