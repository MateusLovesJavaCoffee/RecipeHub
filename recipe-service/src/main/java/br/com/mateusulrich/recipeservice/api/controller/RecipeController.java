package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeOpenApi;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInputData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.update.UpdateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController implements RecipeOpenApi {

    private final RecipeService service;

    @Override
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody RecipeInputData data) {
        RecipeResponse response = service.save(data);
        return ResponseEntity.created(URI.create("/recipes/" + response.id())).body(response);
    }
    @Override
    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable Long recipeId, @RequestBody RecipeInputData data) {
        RecipeResponse response = service.update(recipeId, data);
        return ResponseEntity.ok().body(response);
    }

    @Override
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long recipeId) {
        service.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{recipeId}")
    public ResponseEntity<GetRecipeResponse> findRecipeById(@PathVariable Long recipeId) {
        return ResponseEntity.ok(service.getById(recipeId));
    }

    @Override
    public ResponseEntity<Page<IngredientResponse>> listAllRecipes(Pageable pageable) {
        return null;
    }
}
