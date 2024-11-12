package br.com.mateusulrich.recipeservice.ingredient.mapper;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.stereotype.Component;


@Component
public class IngredientMapper {
    public IngredientMapper() {
    }

    public Ingredient fromCreationDataToEntity(IngredientInputData data) {
        Ingredient ingredient = new Ingredient(
                data.name(),
                data.shortDescription(),
                data.category()
        );
        return ingredient;
    }
    public void update(Ingredient ingredient, IngredientInputData data){
        ingredient.setName(data.name());
        ingredient.setShortDescription(data.shortDescription());
        ingredient.setCategory(data.category());
    }
}
