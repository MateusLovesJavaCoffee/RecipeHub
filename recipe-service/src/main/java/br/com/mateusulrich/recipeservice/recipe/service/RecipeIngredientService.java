package br.com.mateusulrich.recipeservice.recipe.service;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.input.UpdateIngredientCompositionInput;

public interface RecipeIngredientService {

    void create(Integer recipeId, IngredientCompositionInput data);
    void update(Integer recipeId, Integer ingredientId, UpdateIngredientCompositionInput data);
    void delete(Integer recipeId, Integer ingredientId);
}
