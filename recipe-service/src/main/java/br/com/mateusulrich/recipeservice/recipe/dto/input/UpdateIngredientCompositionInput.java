package br.com.mateusulrich.recipeservice.recipe.dto.input;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record UpdateIngredientCompositionInput(

        @NotNull
        Integer unitOfMeasureId,
        @NotNull @Min(1) @Max(9999)
        Integer amount,
        @NotNull @Min(1) @Max(99)
        Integer order,
        @Length(max = 50)
        String description

) implements Serializable {

}