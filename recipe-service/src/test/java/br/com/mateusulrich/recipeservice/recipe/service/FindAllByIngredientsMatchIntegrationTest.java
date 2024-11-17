package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.ServiceIntegrationTests;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;

@ServiceIntegrationTests
public class FindAllByIngredientsMatchIntegrationTest {
    @Autowired
    private RecipeServiceImpl recipeService;

    @Autowired
    private RecipeRepository recipeRepository;
}
