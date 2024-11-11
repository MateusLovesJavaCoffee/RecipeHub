package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeOpenApi;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.CreateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.UpdateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipes")
public class RecipeController implements RecipeOpenApi {
    private final RecipeService recipeService;

    @Override
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(CreateRecipeData data) {
        return null;
    }

    @Override
    public ResponseEntity<RecipeResponse> updateRecipe(Long id, UpdateRecipeData data) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteRecipeById(Long recipeId) {
        return null;
    }

    @Override
    public ResponseEntity<IngredientResponse> findRecipeById(Long ingredientId) {
        return null;
    }

    @Override
    public ResponseEntity<Page<IngredientResponse>> listAllRecipes(Pageable pageable) {
        return null;
    }
}
