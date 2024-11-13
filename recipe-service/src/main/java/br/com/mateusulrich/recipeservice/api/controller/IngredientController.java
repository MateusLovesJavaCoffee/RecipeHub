package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.IngredientOpenApi;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/ingredients")
@RequiredArgsConstructor
public class IngredientController implements IngredientOpenApi {

    private final IngredientService ingredientService;

    @Override
    @PostMapping
    public ResponseEntity<IngredientResponse> addIngredient(@Valid @RequestBody IngredientInputData data) {
        final IngredientResponse response = ingredientService.createIngredient(data);
        return ResponseEntity.created(URI.create("/ingredients/" + response.id())).body(response);
    }

    @Override
    @PutMapping(value = "/{id}")
    public ResponseEntity<IngredientResponse> updateIngredient(@PathVariable(name = "id") Integer id, @RequestBody @Valid IngredientInputData data) {
        ingredientService.updateIngredient(id, data);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(value ="/{ingredientId}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable Integer ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientResponse> findIngredientById(@PathVariable Integer ingredientId) {
        final IngredientResponse response = ingredientService.findIngredientById(ingredientId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<IngredientResponse>> listAllIngredients(
            @RequestParam Optional<String> category,
            @RequestParam Optional<String> name,
            @PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

        Specification<Ingredient> spec = SpecificationTemplate.hasCategoryAndName(category.orElse(null), name.orElse(null));
        return ResponseEntity.ok(ingredientService.listAllIngredients(spec, pageable));
    }
}
