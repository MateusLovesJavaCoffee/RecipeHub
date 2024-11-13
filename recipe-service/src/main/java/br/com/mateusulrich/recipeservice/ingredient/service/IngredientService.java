package br.com.mateusulrich.recipeservice.ingredient.service;


import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface IngredientService {

    IngredientResponse findIngredientById(Integer id);
    IngredientResponse createIngredient(IngredientInputData data);
    void updateIngredient(Integer id, IngredientInputData data);
    void deleteIngredient(Integer ingredientId);
    Page<IngredientResponse> listAllIngredients(Specification<Ingredient> spec, Pageable pageable);

}
