package br.com.mateusulrich.recipeservice.recipe;

import java.util.Set;

public record RecipeIngredientMatchQuery(
        String terms,
        int pageNumber,
        int perPage,
        String sort,
        String direction,
        Set<Long> ingredients
) {
}
