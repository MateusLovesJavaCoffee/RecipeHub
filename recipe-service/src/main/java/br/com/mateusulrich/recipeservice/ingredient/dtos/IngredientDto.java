package br.com.mateusulrich.recipeservice.ingredient.dtos;

import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

public record IngredientDto(

        @NotBlank
        @Length(min = 1, max = 100)
        String name,

        @Length(min = 0, max = 250)
        @JsonProperty(value = "short_description")
        String shortDescription,

        @NotNull
        IngredientCategory category,

        @NotEmpty
        @Size(min = 1, max = 21)
        @JsonProperty(value = "possible_units")
        Set<Long> possibleUnits

) implements Serializable {

}