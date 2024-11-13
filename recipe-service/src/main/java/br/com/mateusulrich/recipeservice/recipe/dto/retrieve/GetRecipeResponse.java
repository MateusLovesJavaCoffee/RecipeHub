package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.data.IngredientCompData;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GetRecipeResponse(
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
        Instant publishedAt,
        List<IngredientCompData> recipeIngredients,
        List<RecipeInstructionInput> recipeSteps

) implements Serializable {
    public static GetRecipeResponse fromEntityToGetResponse(Recipe recipe) {
        return new GetRecipeResponse(
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
                recipe.getPublishedAt(),
                recipe.getIngredientCompositions().stream().map(IngredientCompData::new).toList(),
                recipe.getInstructions().stream().map(
                        x -> new RecipeInstructionInput(x.getInstructionID().getInstructionNumberId(), x.getDescription())
                ).toList()

        );

    }
}