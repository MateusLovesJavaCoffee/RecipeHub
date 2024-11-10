package br.com.mateusulrich.recipeservice.common.exception;

public class NotFoundException extends DomainException {

    protected NotFoundException(String message) {
        super(message, null);
    }

    public static NotFoundException with(final Class<?> entityClass, final Long identifier) {
        return new NotFoundException("%s with ID: %s was not found.".formatted(entityClass.getSimpleName(), identifier.toString()));
    }
}
