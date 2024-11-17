package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.RecipeResponse;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.get.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.list.RecipeIngredientMatchResponse;
import br.com.mateusulrich.recipeservice.api.openapi.RecipeOpenApi;
import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;


@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
@Log4j2
public class RecipeController implements RecipeOpenApi {

    private final RecipeService service;

    @Override
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(@Valid @RequestBody RecipeRequest data) {
        log.debug("POST createRecipe data received {} ", data.toString());
        RecipeResponse response = RecipeResponse.fromEntityToResponseDto(
                service.save(data.toEntity())
        );
        log.debug("POST createRecipe saved id: {} ", response.id());
        log.info("Recipe saved successfully recipeId {} ", response.id());

        return ResponseEntity.created(URI.create("/recipes/" + response.id())).body(response);
    }
    @Override
    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable Integer recipeId, @RequestBody @Valid RecipeRequest data) {
        log.debug("PUT updateRecipe data received {} ", data.toString());

        Recipe recipe = service.findOrThrowNotFound(recipeId);
        recipe.update(data);
        RecipeResponse response = RecipeResponse.fromEntityToResponseDto(
                service.save(recipe)
        );

        log.debug("POST updateRecipe recipeId saved {} ", recipeId);
        log.info("Recipe saved successfully recipeId {} ", recipeId);

        return ResponseEntity.ok().body(response);
    }

    @Override
    @PutMapping("/{recipeId}/activate")
    public ResponseEntity<Void> activate(@PathVariable Integer recipeId) {
        service.changeStatus(recipeId, Boolean.TRUE);
        log.info("Recipe activated successfully recipeId {} ", recipeId);
        return ResponseEntity.ok().build();
    }
    @Override
    @PutMapping("/{recipeId}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Integer recipeId) {
        service.changeStatus(recipeId, Boolean.FALSE);
        log.info("Recipe deactivated successfully recipeId {} ", recipeId);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Integer recipeId) {
        log.debug("DELETE deleteRecipeById recipeId received {} ", recipeId);

        service.delete(recipeId);

        log.debug("DELETE deleteIngredient recipeId deleted {} ", recipeId);
        log.info("Recipe deleted successfully recipeId {} ", recipeId);

        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping("/{recipeId}")
    public ResponseEntity<GetRecipeResponse> findRecipeById(@PathVariable Integer recipeId) {
        return ResponseEntity.ok(
                GetRecipeResponse.fromEntityToGetResponse(
                        service.findOrThrowNotFound(recipeId)));
    }

    @GetMapping(value = "list")
    public ResponseEntity<PageResponse<RecipeIngredientMatchResponse>> listRecipesByIngredients(
            @RequestParam(name = "ingredients", required = true) Set<Integer> ingredients,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size,
            @RequestParam(name = "sort", required = false, defaultValue = "likes") String sort,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction
    ) {
        Specification<Recipe> ingredientSpec = SpecificationTemplate.hasIngredients(ingredients);

        return ResponseEntity.ok().body(
                service.findAllByIngredientsMatchs(ingredients, PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)))
        );
    }



}
