package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;

import java.io.Serializable;
import java.util.List;

public record ListRecipeResponseDto(
        String title,
        String imageUrl,
        List<String> ingredients,
        int preparationTime,
        int servings,
        Difficulty difficulty

) implements Serializable {

}