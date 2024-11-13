package br.com.mateusulrich.recipeservice.recipe.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class RecipeIngredientsID implements Serializable {

    @Column(name = "ingredient_id")
    private Integer ingredientId;
    @Column(name = "recipe_id")
    private Integer recipeId;

    public RecipeIngredientsID(final Integer ingredientId, final Integer recipeId) {
        this.ingredientId = ingredientId;
        this.recipeId = recipeId;
    }
    public RecipeIngredientsID() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientsID that = (RecipeIngredientsID) o;
        return Objects.equals(ingredientId, that.ingredientId) && Objects.equals(recipeId, that.recipeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, recipeId);
    }
}
