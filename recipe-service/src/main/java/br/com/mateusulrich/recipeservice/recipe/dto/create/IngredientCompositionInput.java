package br.com.mateusulrich.recipeservice.recipe.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record IngredientCompositionInput(

        @NotNull
        Long ingredientId,
        @NotNull
        Long unitOfMeasureId,
        @NotNull
        Integer amount,
        @NotNull
        Integer order,
        String description

) implements Serializable {

}