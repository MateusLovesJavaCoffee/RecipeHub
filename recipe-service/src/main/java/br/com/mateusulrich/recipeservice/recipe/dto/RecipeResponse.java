package br.com.mateusulrich.recipeservice.recipe.dto;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecipeResponse(
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
        Instant publishedAt
) {
}
