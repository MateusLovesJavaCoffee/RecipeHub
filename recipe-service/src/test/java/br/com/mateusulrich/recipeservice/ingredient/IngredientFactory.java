package br.com.mateusulrich.recipeservice.ingredient;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;

import java.util.Set;

public class IngredientFactory {

    public static Ingredient newIngredient() {
        return new Ingredient(
                "Ingredient Name Test",
                "Ingredient Description Test",
                IngredientCategory.BAKING
        );
    }
    public static IngredientResponse newIngredientResponse() {
        return new IngredientResponse(
                1L,
                null,
                "Ingredient Name Test",
                "Ingredient Description Test",
                IngredientCategory.BAKING
        );
    }
    public static IngredientInputData newIngredientDto() {
        return new IngredientInputData(
                "Tomato",
                "Fresh tomato",
                IngredientCategory.VEGETABLES,
                Set.of(1L, 2L)
        );
    }
}
