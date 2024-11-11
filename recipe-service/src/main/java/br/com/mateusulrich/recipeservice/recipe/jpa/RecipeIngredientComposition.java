package br.com.mateusulrich.recipeservice.recipe.jpa;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "recipe_ingredient_composition")
public class RecipeIngredientComposition {

    @EmbeddedId
    private RecipeIngredientsID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "order_number", nullable = false)
    private int order;

    @Column(name = "description")
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measure")
    private IngredientUnit unitOfMeasure;

    public RecipeIngredientComposition(Recipe recipe, Ingredient ingredient, Integer amount, int order, String description, IngredientUnit unitOfMeasure) {
        this.id = new RecipeIngredientsID(ingredient.getId(), recipe.getId());
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.order = order;
        this.description = description;
        this.unitOfMeasure = unitOfMeasure;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipeIngredientComposition that = (RecipeIngredientComposition) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
