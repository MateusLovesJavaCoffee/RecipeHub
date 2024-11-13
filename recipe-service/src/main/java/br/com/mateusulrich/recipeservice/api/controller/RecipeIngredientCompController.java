package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeIngredientCompOpenApi;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.input.UpdateIngredientCompositionInput;
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
public class RecipeIngredientCompController implements RecipeIngredientCompOpenApi {

    private final RecipeIngredientService service;

    @Override
    @PostMapping
    public ResponseEntity<Void> addIngredientIntoRecipe(@PathVariable Integer recipeId, @RequestBody @Valid IngredientCompositionInput input) {
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
            @RequestBody @Valid UpdateIngredientCompositionInput input) {

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
