package br.com.mateusulrich.recipeservice.recipe.repositories.projections;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;

import java.util.List;

public record RecipePreview(
        Integer id,
        String title,
        String imageUrl,
        int likes,
        int readyInMinutes,
        Difficulty difficulty,
        List<IngredientPreview> ingredients,
        Long ingredientMatchCount


) {

    public record IngredientPreview(
            Integer id,
            String name
    ) {

    }

}
