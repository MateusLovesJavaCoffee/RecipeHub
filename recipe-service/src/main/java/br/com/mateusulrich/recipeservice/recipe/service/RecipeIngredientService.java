package br.com.mateusulrich.recipeservice.recipe.service;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.UpdateRecipeIngredientRequest;

public interface RecipeIngredientService {

    void create(Integer recipeId, RecipeIngredientRequest data);
    void update(Integer recipeId, Integer ingredientId, UpdateRecipeIngredientRequest data);
    void delete(Integer recipeId, Integer ingredientId);
}
