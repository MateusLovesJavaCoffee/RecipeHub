package br.com.mateusulrich.recipeservice.ingredient.service;


import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientDto;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IngredientService {

    IngredientResponse findByIdOrThrowNotFound(Long id);
    IngredientResponse createIngredient(IngredientDto data);
    IngredientResponse updateIngredient(Long id, IngredientDto data);
//    void updateIngredientImage(Long id, CreateMediaData createMediaData);
    void deleteIngredient(Long ingredientId);
    Page<IngredientResponse> listAllIngredients(Pageable pageable);
}
