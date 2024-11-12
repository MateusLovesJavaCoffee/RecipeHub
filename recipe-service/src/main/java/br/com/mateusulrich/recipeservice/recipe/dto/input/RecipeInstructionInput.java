package br.com.mateusulrich.recipeservice.recipe.dto.input;


import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record RecipeInstructionInput(
        @NotNull @Min(1) @Max(99)
        Integer stepNumber,

        @NotBlank
        @Length(min = 1, max = 250)
        String description
) implements Serializable {
}