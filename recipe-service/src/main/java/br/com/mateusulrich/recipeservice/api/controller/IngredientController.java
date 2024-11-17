package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientResponse;
import br.com.mateusulrich.recipeservice.api.openapi.IngredientOpenApi;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/ingredients")
@RequiredArgsConstructor
@Log4j2
public class IngredientController implements IngredientOpenApi {

    private final IngredientService ingredientService;

    @Override
    @PostMapping
    public ResponseEntity<IngredientResponse> createIngredient(@Valid @RequestBody IngredientRequest data) {
        log.debug("POST addIngredient data received {} ", data.toString());
        final Ingredient response = ingredientService.save(data.possibleUnits(), data.toEntity());
        log.debug("POST addIngredient ingredientId saved {} ", response.getId());
        log.info("Ingredient saved successfully ingredientId {} ", response.getId());
        return ResponseEntity.created(URI.create("/ingredients/" + response.getId())).body(IngredientResponse.from(response));
    }

    @Override
    @PutMapping(value = "/{ingredientId}")
    public ResponseEntity<Void> updateIngredient(@PathVariable Integer ingredientId, @RequestBody @Valid IngredientRequest data) {
        log.debug("PUT updateIngredient data received {} ", data.toString());

        ingredientService.update(ingredientId, data);

        log.debug("POST updateIngredient ingredientId saved {} ", ingredientId);
        log.info("Ingredient saved successfully ingredientId {} ", ingredientId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(value ="/{ingredientId}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer ingredientId) {
        log.debug("DELETE deleteIngredientById ingredientId received {} ", ingredientId);
        ingredientService.delete(ingredientId);
        log.debug("DELETE deleteIngredient ingredientId deleted {} ", ingredientId);
        log.info("Ingredient deleted successfully ingredientId {} ", ingredientId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientResponse> findIngredient(@PathVariable Integer ingredientId) {
        final Ingredient ingredient = ingredientService.findOrThrowNotFound(ingredientId);
        return ResponseEntity.ok(IngredientResponse.from(ingredient));
    }

    @GetMapping
    @Override
    public ResponseEntity<Page<IngredientResponse>> listAllIngredients(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String name,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "name") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    ) {
        Specification<Ingredient> spec = SpecificationTemplate
                .hasCategoryAndName(category == null ? null : category.isBlank() ? null : IngredientCategory
                        .valueOf(category.toUpperCase()), name);

        return ResponseEntity.ok(ingredientService.listAllIngredients(spec, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
                .map(IngredientResponse::from));
    }
}
