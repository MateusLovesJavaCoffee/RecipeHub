package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeIngredientCompOpenApi;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;
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
        service.create(recipeId, input);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    @Override
    public ResponseEntity<Void> updateIngredientIntoRecipe(
            @PathVariable Long recipeId,
            @RequestBody @Valid IngredientCompositionInput input) {

        service.update(recipeId, input);
        return ResponseEntity.noContent().build();
    }
    @Override
    @DeleteMapping(value = "/{ingredientId}")
    public ResponseEntity<Void> deleteIngredientIntoRecipe(@PathVariable Long recipeId, @PathVariable Long ingredientId) {
         service.delete(recipeId, ingredientId);
         return ResponseEntity.noContent().build();
    }
}
