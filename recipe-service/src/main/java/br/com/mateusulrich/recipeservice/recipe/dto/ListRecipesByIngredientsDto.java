package br.com.mateusulrich.recipeservice.recipe.dto;

import java.util.List;

public class ListRecipesByIngredientsDto {

    private Long id;
    private String title;
    private String imageUrl;
    private int missedIngredientCount;
    private int usedIngredientCount;
    private int unusedIngredientCount;
    private List<String> missedIngredients;
    private List<String> usedIngredients;
    private List<String> unusedIngredients;
    private int compatiblePercentage;

}
