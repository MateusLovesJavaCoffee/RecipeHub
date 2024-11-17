package br.com.mateusulrich.recipeservice.api.dtos.ingredient;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public record IngredientResponse(
        Integer id,
        String imageUrl,
        String name,
        String description,
        IngredientCategory category,
        List<PossibleUnits> possibleUnits

) {
        public record PossibleUnits(
                Integer id,
                String name
        ) {

        }
        public static IngredientResponse from(Ingredient ingredient){
                return new IngredientResponse(
                        ingredient.getId(),
                        ingredient.getImageUrl(),
                        ingredient.getName(),
                        ingredient.getDescription(),
                        ingredient.getCategory(),
                        ingredient.getPossibleUnits().stream().map(
                                x -> new PossibleUnits(x.getId(), x.getName())
                        ).toList()

                );

        }
}
