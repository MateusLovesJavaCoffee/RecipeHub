package br.com.mateusulrich.recipeservice.recipe.dto.create;


import java.io.Serializable;

public record CreateRecipeStepData(
        Integer stepNumber,
        String description
) implements Serializable {
}