package br.com.mateusulrich.recipeservice.common.exception;

public class IngredientAlreadyExistsException extends DomainException{
    public IngredientAlreadyExistsException(String message) {
        super(message, null);
    }
}
