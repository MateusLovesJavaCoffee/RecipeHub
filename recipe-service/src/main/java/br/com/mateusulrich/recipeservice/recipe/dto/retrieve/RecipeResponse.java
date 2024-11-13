package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record RecipeResponse(
        Integer id,
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

    public static RecipeResponse fromEntityToResponseDto(Recipe recipe) {
        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getImgUrl(),
                recipe.getLikes(),
                recipe.getPreparationMinutes(),
                recipe.getCookingMinutes(),
                recipe.getDescription(),
                recipe.getServings(),
                recipe.getDifficulty(),
                recipe.getEstimatedCost(),
                recipe.getPublishedAt())

                ;
    }
}
