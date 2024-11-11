package br.com.mateusulrich.recipeservice.recipe.service.impl;

import br.com.mateusulrich.recipeservice.recipe.dto.CreateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    public RecipeResponse save(CreateRecipeData createRecipeData) {
        return null;
    }
}
