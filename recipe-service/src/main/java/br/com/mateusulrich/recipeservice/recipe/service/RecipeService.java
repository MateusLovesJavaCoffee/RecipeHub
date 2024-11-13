package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.ListRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;


public interface RecipeService {
    GetRecipeResponse getById(Integer id);
    RecipeResponse create(RecipeInput recipeInput);
    RecipeResponse update(Integer id, RecipeInput createRecipeData);
    PageResponse<ListRecipeResponse> findAllByIngredients(Set<Integer> ingredients, Specification<Recipe> spec, Pageable pageable);
    void delete(Integer id);
    void changeStatus(Integer id, Boolean status);
}
