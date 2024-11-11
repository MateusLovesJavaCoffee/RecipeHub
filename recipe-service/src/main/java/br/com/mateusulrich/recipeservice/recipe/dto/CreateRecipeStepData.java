package br.com.mateusulrich.recipeservice.recipe.dto;


import java.io.Serializable;

public record CreateRecipeStepData(
        Integer stepNumber,
        String description
) implements Serializable {
}