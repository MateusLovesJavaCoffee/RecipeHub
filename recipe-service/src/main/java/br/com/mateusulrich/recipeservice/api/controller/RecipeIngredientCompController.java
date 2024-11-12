package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeIngredientCompOpenApi;
import br.com.mateusulrich.recipeservice.recipe.dto.create.IngredientCompositionInput;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/recipes/{recipeId}/ingredients")
@RequiredArgsConstructor
public class RecipeIngredientCompController implements RecipeIngredientCompOpenApi {

    private final RecipeIngredientService service;

    @Override
    @PostMapping
    public ResponseEntity<Void> addIngredientIntoRecipe(@PathVariable Long recipeId, @RequestBody @Valid IngredientCompositionInput input) {
        service.save(recipeId, input);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> updateIngredientIntoRecipe(IngredientCompositionInput input) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteIngredientIntoRecipe(Long recipeId, Long ingredientId) {
        return null;
    }
}
