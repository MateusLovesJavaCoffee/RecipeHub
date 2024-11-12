package br.com.mateusulrich.recipeservice.recipe.service;
import br.com.mateusulrich.recipeservice.recipe.dto.create.IngredientCompositionInput;

public interface RecipeIngredientService {

    void save(Long recipeId, IngredientCompositionInput data);
    void update(Long id, IngredientCompositionInput data);
    void delete(Long id);
}
