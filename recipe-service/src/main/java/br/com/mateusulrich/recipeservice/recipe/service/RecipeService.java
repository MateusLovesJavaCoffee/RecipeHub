package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.recipe.dto.CreateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;

public interface RecipeService {

    RecipeResponse save(CreateRecipeData createRecipeData);
}
