package br.com.mateusulrich.recipeservice.common.pagination;

import java.util.Set;

public record RecipeSearchQuery(
        String terms,
        int pageNumber,
        int perPage,
        String sort,
        String direction,
        Set<Long> ingredients
) {
}
