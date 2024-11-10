package br.com.mateusulrich.recipeservice.common.exception;

public class IngredientNameAlreadyExistsException extends DomainException{

    public IngredientNameAlreadyExistsException(String message) {
        super(message, null);
    }
}
