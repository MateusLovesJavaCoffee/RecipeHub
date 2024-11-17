package br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.data;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.recipe.entity.RecipeIngredient;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RecipeIngredient}
 */
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IngredientCompData implements Serializable {
    IngredientDto ingredient;
    Integer amount;
    int order;
    String description;
    IngredientUnitDto unitOfMeasure;

    /**
     * DTO for {@link Ingredient}
     */
    @Value
    public static class IngredientDto implements Serializable {
        Integer id;
        String name;
        String imageUrl;
        String shortDescription;
        IngredientCategory category;
    }

    /**
     * DTO for {@link UnitOfMeasure}
     */
    @Value
    public static class IngredientUnitDto implements Serializable {
        Integer id;
        String name;
    }

    public IngredientCompData(RecipeIngredient composition) {
        Ingredient ing = composition.getIngredient();
        UnitOfMeasure uof = composition.getUnitOfMeasure();
        this.ingredient = new IngredientDto(
                ing.getId(),
                ing.getName(),
                ing.getImageUrl(),
                ing.getDescription(),
                ing.getCategory()
        );
        this.amount = composition.getAmount();
        this.order = composition.getOrder();
        this.description = composition.getDescription();
        this.unitOfMeasure = new IngredientUnitDto(
                uof.getId(),
                uof.getName()
        );
    }
}