
package br.com.mateusulrich.recipeservice.recipe.dto;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;

import java.io.Serializable;
import java.util.List;

public record UpdateRecipeData(

        String title,
        int preparationTime,
        Integer servings,
        Difficulty difficulty,
        List<RecipeIngredientsDto> recipeIngredients,
        List<CreateRecipeStepData> recipeSteps

) implements Serializable {
}