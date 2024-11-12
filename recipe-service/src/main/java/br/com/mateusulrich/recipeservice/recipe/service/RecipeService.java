package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInputData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;

public interface RecipeService {
    GetRecipeResponse getById(Long id);
    RecipeResponse save(RecipeInputData recipeInputData);
    RecipeResponse update(Long id, RecipeInputData createRecipeData);
    void delete(Long id);
}
