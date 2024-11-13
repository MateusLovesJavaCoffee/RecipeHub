package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeOpenApi;
import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.ListRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;


@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController implements RecipeOpenApi {

    private final RecipeService service;

    @Override
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody RecipeInput data) {
        RecipeResponse response = service.create(data);
        return ResponseEntity.created(URI.create("/recipes/" + response.id())).body(response);
    }
    @Override
    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable Integer recipeId, @RequestBody @Valid RecipeInput data) {
        RecipeResponse response = service.update(recipeId, data);
        return ResponseEntity.ok().body(response);
    }


    @Override
    @PutMapping("/{recipeId}/activate")
    public ResponseEntity<Void> activate(@PathVariable Integer recipeId) {
        service.changeStatus(recipeId, Boolean.TRUE);
        return ResponseEntity.ok().build();
    }
    @Override
    @PutMapping("/{recipeId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Integer recipeId) {
        service.changeStatus(recipeId, Boolean.FALSE);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Integer recipeId) {
        service.delete(recipeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{recipeId}")
    public ResponseEntity<GetRecipeResponse> findRecipeById(@PathVariable Integer recipeId) {
        return ResponseEntity.ok(service.getById(recipeId));
    }
    @GetMapping
    public ResponseEntity<PageResponse<ListRecipeResponse>> listRecipesByIngredients(
            @RequestParam(name = "ingredients", required = false) Set<Integer> ingredients,
            @PageableDefault(page = 0, size = 10, sort = "likes", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        Specification<Recipe> ingredientSpec = SpecificationTemplate.hasIngredients(ingredients);
       return ResponseEntity.ok().body(
                service.findAllByIngredients(ingredients, ingredientSpec, pageable)
        );
    }




}
