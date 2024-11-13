package br.com.mateusulrich.recipeservice.recipe.dto.retrieve.data;

import br.com.mateusulrich.recipeservice.recipe.entity.IngredientComposition;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IngredientData {
    private Integer ingredientId;
    private String ingredientName;

    public IngredientData(Integer ingredientId, String ingredientName) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
    }
    public static IngredientData fromIngredient(IngredientComposition composition) {
        return new IngredientData(
                composition.getId().getIngredientId(),
                composition.getIngredient().getName()
        );

    }
}
