package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.list.RecipeIngredientMatchResponse;
import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;


public interface RecipeService {
    void updatePhoto(Recipe recipe, MultipartFile file) throws IOException;
    Recipe findOrThrowNotFound(Integer id);
    Recipe save(Recipe recipe);
    PageResponse<RecipeIngredientMatchResponse> findAllByIngredientsMatchs(Set<Integer> ingredients, Pageable pageable);
    void delete(Integer id);
    void changeStatus(Integer id, Boolean status);
}
