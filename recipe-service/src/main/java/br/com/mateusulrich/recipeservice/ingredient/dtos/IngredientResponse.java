package br.com.mateusulrich.recipeservice.ingredient.dtos;

import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IngredientResponse(
        Long id,
        String imageUrl,
        String name,
        String description,
        IngredientCategory category

) {
    public static IngredientResponse from(final Ingredient ingredient) {
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getImageUrl(),
                ingredient.getName(),
                ingredient.getShortDescription(),
                ingredient.getCategory()
        );
    }
}
