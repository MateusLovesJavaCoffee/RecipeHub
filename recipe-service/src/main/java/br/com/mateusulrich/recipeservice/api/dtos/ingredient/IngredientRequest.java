package br.com.mateusulrich.recipeservice.api.dtos.ingredient;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

public record IngredientRequest(

        @Schema(
                description = "Nome do ingrediente",
                example = "Tomate"
        )
        @NotBlank
        @Length(min = 3, max = 100)
        String name,

        @Schema(
                description = "Descrição do ingrediente, podendo ser uma descrição opcional mais detalhada.",
                example = "Tomate maduro, utilizado para saladas e molhos."
        )
        @Length(min = 0, max = 250)
        String description,

        @Schema(
                description = "Categoria do ingrediente, como 'Verduras', 'Frutas', 'Carnes', etc.",
                example = "VEGETABLES"
        )
        @NotNull
        IngredientCategory category,

        @Schema(
                description = "Unidades possíveis para o ingrediente (ex: 'kg', 'g', 'unidade', etc.).",
                example = "[1, 2, 3]"
        )
        @NotEmpty
        @Size(min = 1, max = 21)
        Set<Integer> possibleUnits

) implements Serializable {

        public Ingredient toEntity() {
                return new Ingredient(
                        this.name, this.description, this.category
                );
        }

}
