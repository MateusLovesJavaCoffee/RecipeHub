package br.com.mateusulrich.recipeservice.ingredient.service;


import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

public interface IngredientService {
    void updatePhoto(Ingredient ingredient, MultipartFile file) throws IOException;
    Ingredient findOrThrowNotFound(Integer id);
    Ingredient save(Set<Integer> unitIds, Ingredient data);
    Ingredient update(Integer id, IngredientRequest data);
    void delete(Integer ingredientId);
    Page<Ingredient> listAllIngredients(Specification<Ingredient> spec, Pageable pageable);

}
