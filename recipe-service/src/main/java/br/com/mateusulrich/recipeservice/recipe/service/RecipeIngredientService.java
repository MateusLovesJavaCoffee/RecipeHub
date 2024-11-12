package br.com.mateusulrich.recipeservice.recipe.service;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;

public interface RecipeIngredientService {

    void create(Long recipeId, IngredientCompositionInput data);
    void update(Long recipeId, IngredientCompositionInput data);
    void delete(Long recipeId, Long ingredientId);
}
