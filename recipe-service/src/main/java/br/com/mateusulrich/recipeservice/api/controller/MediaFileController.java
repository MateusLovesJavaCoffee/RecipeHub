package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.dtos.photo.ImageMediaRequest;
import br.com.mateusulrich.recipeservice.api.openapi.MediaFileOpenApi;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class MediaFileController implements MediaFileOpenApi {

    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    @PutMapping(value = "/ingredients/{ingredientId}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public void updateIngredientPhoto(@PathVariable Integer ingredientId,
                                      @ModelAttribute @Valid ImageMediaRequest input) throws IOException {
        Ingredient ingredient = ingredientService.findOrThrowNotFound(ingredientId);
        ingredientService.updatePhoto(ingredient, input.file());
    }

    @PutMapping(value = "/recipes/{recipeId}/photo",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Override
    public void updateRecipePhoto(@PathVariable Integer recipeId, @ModelAttribute @Valid ImageMediaRequest input) throws IOException {
        Recipe recipe = recipeService.findOrThrowNotFound(recipeId);
        recipeService.updatePhoto(recipe, input.file());
    }

}
