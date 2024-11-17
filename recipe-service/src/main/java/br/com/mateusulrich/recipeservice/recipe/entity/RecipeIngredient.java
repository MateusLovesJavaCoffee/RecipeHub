package br.com.mateusulrich.recipeservice.recipe.entity;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Table(name = "recipe_ingredients")
public class RecipeIngredient {

    @EmbeddedId
    private RecipeIngredientsID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "order_number", nullable = false)
    private int order;

    @Column(name = "description", length = 50)
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measure_id")
    private UnitOfMeasure unitOfMeasure;

    public RecipeIngredient(Recipe recipe, Ingredient ingredient, Integer amount, int order, String description, UnitOfMeasure unitOfMeasure) {
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
        RecipeIngredient that = (RecipeIngredient) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
