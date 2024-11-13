package br.com.mateusulrich.recipeservice.ingredient.dtos;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record IngredientResponse(
        Integer id,
        String imageUrl,
        String name,
        String description,
        IngredientCategory category

) {

        public static IngredientResponse from(Ingredient ingredient){
                return new IngredientResponse(
                        ingredient.getId(),
                        ingredient.getImageUrl(),
                        ingredient.getName(),
                        ingredient.getDescription(),
                        ingredient.getCategory()
                );

        }
}
