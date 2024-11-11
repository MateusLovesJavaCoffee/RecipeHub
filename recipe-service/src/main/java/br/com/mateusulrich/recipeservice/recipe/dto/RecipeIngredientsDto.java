package br.com.mateusulrich.recipeservice.recipe.dto;

import lombok.Value;

import java.io.Serializable;

public record RecipeIngredientsDto(
        Long ingredientId,
        Integer amount,
        int order,
        String description

) implements Serializable {

}