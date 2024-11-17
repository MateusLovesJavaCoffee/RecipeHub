package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeIngredientOpenApi;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.UpdateRecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/recipes/{recipeId}/ingredients")
@RequiredArgsConstructor
public class RecipeIngredientsController implements RecipeIngredientOpenApi {

    private final RecipeIngredientService service;

    @Override
    @PostMapping
    public ResponseEntity<Void> addIngredientIntoRecipe(@PathVariable Integer recipeId, @RequestBody @Valid RecipeIngredientRequest input) {
        service.create(recipeId, input);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(input.ingredientId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{ingredientId}")
    @Override
    public ResponseEntity<Void> updateIngredientIntoRecipe(
            @PathVariable Integer ingredientId,
            @PathVariable Integer recipeId,
            @RequestBody @Valid UpdateRecipeIngredientRequest input) {

        service.update(recipeId,ingredientId, input);
        return ResponseEntity.noContent().build();
    }
    @Override
    @DeleteMapping(value = "/{ingredientId}")
    public ResponseEntity<Void> deleteIngredientIntoRecipe(@PathVariable Integer recipeId, @PathVariable Integer ingredientId) {
         service.delete(recipeId, ingredientId);
         return ResponseEntity.noContent().build();
    }
}
