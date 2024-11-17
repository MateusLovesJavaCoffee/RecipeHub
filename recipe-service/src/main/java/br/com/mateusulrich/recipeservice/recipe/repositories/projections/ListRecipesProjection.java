package br.com.mateusulrich.recipeservice.recipe.repositories.projections;

public interface ListRecipesProjection {

    Integer getId();
    String getTitle();
    String getImageUrl();
    int getLikes();
    int getReadyInMinutes();
    String getDifficulty();
    Long getIngredientMatchCount();
}
