package br.com.mateusulrich.recipeservice.ingredient.service;


import br.com.mateusulrich.recipeservice.ingredient.dtos.CreateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.dtos.UpdateIngredientData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IngredientService {

    IngredientResponse findByIdOrThrowNotFound(Long id);
    IngredientResponse createIngredient(CreateIngredientData data);
    IngredientResponse updateIngredient(UpdateIngredientData data);
//    void updateIngredientImage(Long id, CreateMediaData createMediaData);
    void deleteIngredient(Long ingredientId);
    Page<IngredientResponse> listAllIngredients(Pageable pageable);
}
