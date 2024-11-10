package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.IngredientOpenApi;
import br.com.mateusulrich.recipeservice.ingredient.dtos.CreateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.dtos.UpdateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/ingredients")
@RequiredArgsConstructor
@Log4j2
public class IngredientController implements IngredientOpenApi {

    private final IngredientService ingredientService;

    @Override
    @PostMapping(
            produces = APPLICATION_JSON_VALUE,
            consumes = APPLICATION_JSON_VALUE
    )
    public ResponseEntity<IngredientResponse> addIngredient(@Valid @RequestBody CreateIngredientData data) {
        log.debug("POST createIngredient data received {} ", data.toString());
        final IngredientResponse response = ingredientService.createIngredient(data);

        log.info("Ingredient saved successfully ingredientId {} ", response.id());


        return ResponseEntity.created(URI.create("/ingredients/" + response.id())).body(response);
    }

    @Override
    @PutMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientResponse> updateIngredient(@RequestBody @Valid UpdateIngredientData data) {
        ingredientService.updateIngredient(data);
        return ResponseEntity.noContent().build();
    }

//    @Override
//    @PutMapping(
//            value = "/{ingredientId}",
//            consumes = MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Void> updateIngredientPhoto(
//            @PathVariable(name = "ingredientId") Long ingredientId,
//            @RequestParam(name = "ingredientPhoto", required = true) MultipartFile file) {
//
//        ingredientService.updateIngredientImage(ingredientId, CreateMediaData.resourceOf(file));
//        return ResponseEntity.noContent().build();
//    }

    @Override
    @DeleteMapping(value ="/{ingredientId}")
    public ResponseEntity<Void> deleteIngredientById(@PathVariable Long ingredientId) {
        ingredientService.deleteIngredient(ingredientId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping(value = "/{ingredientId}")
    public ResponseEntity<IngredientResponse> findIngredientById(@PathVariable Long ingredientId) {
        final IngredientResponse response = ingredientService.getIngredientById(ingredientId);
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<IngredientResponse>> listAllIngredients(@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(ingredientService.listAllIngredients(pageable));
    }
}
