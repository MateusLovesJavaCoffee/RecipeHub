package br.com.mateusulrich.recipeservice.recipe.mapper;

import br.com.mateusulrich.recipeservice.recipe.dto.RecipeIngredientCompData;
import br.com.mateusulrich.recipeservice.recipe.dto.create.CreateRecipeStepData;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInputData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.jpa.Recipe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class RecipeMapper {

    public void update(Recipe recipe, RecipeInputData data) {
        recipe.setCookingMinutes(data.cookingMinutes())
                .setDescription(data.description())
                .setDifficulty(data.difficulty())
                .setTitle(data.title())
                .setEstimatedCost(data.estimatedCost())
                .setPreparationMinutes(data.preparationMinutes())
                .setServings(data.servings());
    }
    public Recipe fromCreationDtoToEntity(RecipeInputData data) {
        return new Recipe(
                data.title(),
                data.description(),
                data.preparationMinutes(),
                data.cookingMinutes(),
                data.servings(),
                data.difficulty(),
                data.estimatedCost()


        );
    }

    public RecipeResponse fromEntityToResponseDto(Recipe recipe) {
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
    public GetRecipeResponse fromEntityToGetResponse(Recipe recipe) {
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
                    recipe.getIngredientCompositions().stream().map(RecipeIngredientCompData::new).toList(),
                    recipe.getRecipeSteps().stream().map(
                            x -> new CreateRecipeStepData(x.getStepNumber(), x.getDescription())
                    ).toList()

            );

    }
}
