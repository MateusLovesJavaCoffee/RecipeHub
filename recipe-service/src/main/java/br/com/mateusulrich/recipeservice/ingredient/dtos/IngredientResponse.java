package br.com.mateusulrich.recipeservice.ingredient.dtos;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record IngredientResponse(
        Long id,
        @JsonProperty(value = "image_url")
        String imageUrl,
        String name,
        @JsonProperty(value = "short_description")
        String shortDescription,
        IngredientCategory category

) {

        public static IngredientResponse from(Ingredient ingredient){
                return new IngredientResponse(
                        ingredient.getId(),
                        ingredient.getImageUrl(),
                        ingredient.getName(),
                        ingredient.getShortDescription(),
                        ingredient.getCategory()
                );

        }
}
