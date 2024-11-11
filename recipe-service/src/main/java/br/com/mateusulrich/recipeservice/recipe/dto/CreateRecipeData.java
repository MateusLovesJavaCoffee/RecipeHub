package br.com.mateusulrich.recipeservice.recipe.dto;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

public record CreateRecipeData(

        @NotBlank
        @Length(min = 1, max = 100)
        String title,

        @NotNull
        @JsonProperty(value = "preparation_time")
        int preparationTime,

        @NotNull
        int servings,

        @NotNull
        Difficulty difficulty,

        @NotNull
        @JsonProperty(value = "estimated_cost")
        int estimatedCost,

        @NotEmpty
        @Size(min = 1, max = 50)
        List<RecipeIngredientsDto> ingredients,

        @NotEmpty
        @Size(min = 1, max = 50)
        List<CreateRecipeStepData> recipeSteps

) implements Serializable {


}