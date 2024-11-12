package br.com.mateusulrich.recipeservice.recipe.dto;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.recipe.entity.RecipeIngredientComposition;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link RecipeIngredientComposition}
 */
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RecipeIngredientCompData implements Serializable {
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
        Long id;
        String name;
        String imageUrl;
        String shortDescription;
        IngredientCategory category;
    }

    /**
     * DTO for {@link IngredientUnit}
     */
    @Value
    public static class IngredientUnitDto implements Serializable {
        Long id;
        String name;
    }

    public RecipeIngredientCompData(RecipeIngredientComposition composition) {
        Ingredient ing = composition.getIngredient();
        IngredientUnit uof = composition.getUnitOfMeasure();
        this.ingredient = new IngredientDto(
                ing.getId(),
                ing.getName(),
                ing.getImageUrl(),
                ing.getShortDescription(),
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