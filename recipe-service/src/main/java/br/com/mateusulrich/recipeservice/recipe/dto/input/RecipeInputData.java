package br.com.mateusulrich.recipeservice.recipe.dto.input;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;

public record RecipeInputData(

        @NotBlank
        @Length(min = 1, max = 100)
        String title,

        @NotNull
        @Min(value = 1)
        @Max(value = 999)
        @JsonProperty(value = "preparation_minutes")
        Integer preparationMinutes,

        @JsonProperty(value = "cooking_minutes")
        @PositiveOrZero
        @Max(value = 999)
        Integer cookingMinutes,

        @Length(min = 1, max = 1000)
        String description,

        @NotNull
        @Min(value = 1)
        @Max(value = 99)
        Integer servings,

        Difficulty difficulty,

        @NotNull
        @Min(value = 1)
        @Max(value = 999)
        @JsonProperty(value = "estimated_cost")
        Integer estimatedCost

) implements Serializable { }
