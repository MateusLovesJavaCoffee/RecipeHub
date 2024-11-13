package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ListRecipesByIngredientsCounting {

    private Integer id;
    private String title;
    private String imageUrl;
    private int likes;
    private int readyInMinutes;
    private Difficulty difficulty;
    private int missedIngredientCount;
    private int usedIngredientCount;
    private int unusedIngredientCount;
    private Long ingredientCount;


}

