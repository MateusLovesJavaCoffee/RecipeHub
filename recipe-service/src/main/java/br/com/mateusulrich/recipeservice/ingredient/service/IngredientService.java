package br.com.mateusulrich.recipeservice.ingredient.service;


import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate.*;

public interface IngredientService {

    IngredientResponse findIngredientById(Long id);
    IngredientResponse createIngredient(IngredientInputData data);
    void updateIngredient(Long id, IngredientInputData data);
    void deleteIngredient(Long ingredientId);
    Page<IngredientResponse> listAllIngredients(IngredientSpec spec, Pageable pageable);

}
