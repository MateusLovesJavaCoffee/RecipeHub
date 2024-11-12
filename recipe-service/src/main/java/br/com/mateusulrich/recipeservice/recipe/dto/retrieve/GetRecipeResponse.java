package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.dto.RecipeIngredientCompData;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetRecipeResponse(
        Long id,
        String title,
        String imageUrl,
        int likes,
        int preparationTime,
        Integer cookingMinutes,
        String description,
        Integer servings,
        Difficulty difficulty,
        int estimatedCost,
        Instant publishedAt,
        List<RecipeIngredientCompData> recipeIngredients,
        List<RecipeInstructionInput> recipeSteps

) implements Serializable {
}