package br.com.mateusulrich.recipeservice.ingredient.mapper;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientDto;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.stereotype.Component;


@Component
public class IngredientMapper {
    public IngredientMapper() {
    }
    public IngredientResponse toIngredientResponse(Ingredient ingredient){
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getImageUrl(),
                ingredient.getName(),
                ingredient.getShortDescription(),
                ingredient.getCategory()
        );

    }
    public Ingredient fromCreationDataToEntity(IngredientDto data) {
        Ingredient ingredient = new Ingredient(
                data.name(),
                data.shortDescription(),
                data.category()
        );
        return ingredient;
    }
    public void update(Ingredient ingredient, IngredientDto data){
        ingredient.setName(data.name());
        ingredient.setShortDescription(data.shortDescription());
        ingredient.setCategory(data.category());
    }
}
