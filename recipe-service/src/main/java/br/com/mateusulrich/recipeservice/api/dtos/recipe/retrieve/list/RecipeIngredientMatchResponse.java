package br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.list;

import br.com.mateusulrich.recipeservice.recipe.repositories.projections.ListRecipesProjection;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class RecipeIngredientMatchResponse {

    private Integer id;
    private String title;
    private String imageUrl;
    private int likes;
    private int readyInMinutes;
    private String difficulty;
    private long missedIngredientCount;
    private long usedIngredientCount;
    private int compatiblePercentage;

    public RecipeIngredientMatchResponse(Set<Integer> ingredients, ListRecipesProjection projection) {
        this.id = projection.getId();
        this.title = projection.getTitle();
        this.imageUrl = projection.getImageUrl();
        this.likes = projection.getLikes();
        this.readyInMinutes = projection.getReadyInMinutes();
        this.difficulty = projection.getDifficulty();
        this.missedIngredientCount = ingredients.size() - projection.getIngredientMatchCount();
        this.usedIngredientCount = projection.getIngredientMatchCount();
        this.compatiblePercentage = (int) Math.round(((double) projection.getIngredientMatchCount() / ingredients.size()) * 100);}

}

