package br.com.mateusulrich.recipeservice.recipe.dto.retrieve;

import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.data.IngredientData;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class ListRecipeResponse {
    private Integer id;
    private String title;
    private String imgUrl;
    private int readyInMinutes;
    private Difficulty difficulty;
    private int likes;
    private Set<IngredientData> ingredients;
    private int ingredientCount;
    boolean fullCompatible;

    public ListRecipeResponse(Integer id, String title, String imgUrl, int readyInMinutes, Difficulty difficulty, int likes, Set<IngredientData> ingredients, int ingredientCount) {
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
        this.readyInMinutes = readyInMinutes;
        this.difficulty = difficulty;
        this.likes = likes;
        this.ingredients = ingredients != null ? ingredients : Set.of(); // Protege contra null
        this.ingredientCount = ingredientCount;
    }

    public static ListRecipeResponse fromEntityToListResponse(Set<Integer> ingredients, Recipe recipe) {
        int count = 0;
        Set<IngredientData> ingredientDataSet = (recipe.getIngredientCompositions() != null && !recipe.getIngredientCompositions().isEmpty())
                ? recipe.getIngredientCompositions().stream().map(IngredientData::fromIngredient).collect(Collectors.toSet())
                : Set.of();

        ListRecipeResponse response = new ListRecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getImgUrl(),
                recipe.getReadyInMinutes(),
                recipe.getDifficulty(),
                recipe.getLikes(),
                ingredientDataSet,
                0);
        if (ingredients != null && !ingredients.isEmpty()) {
            for (IngredientData dto : response.getIngredients()) {
                if (ingredients.contains(dto.getIngredientId())) {
                    count++;
                }
            }

            if (count == ingredients.size()) {
                response.setFullCompatible(true);
            }
        }

        response.setIngredientCount(count);
        return response;
    }
}
