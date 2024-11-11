package br.com.mateusulrich.recipeservice.recipe.jpa;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "recipes")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(length = 250)
    private String imgUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "preparation_minutes", nullable = false)
    private int preparationMinutes;

    @Column(name = "ready_in_minutes", nullable = false)
    private int readyInMinutes;

    @Column(name = "cooking_minutes")
    private Integer cookingMinutes;

    @Column(nullable = false)
    private int servings;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Difficulty difficulty;

    @Column(name = "estimated_cost", nullable = false)
    private int estimatedCost;

    @Column(name = "likes", nullable = false)
    private int likes = 0;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeIngredientComposition> ingredientCompositions = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecipeStep> recipeSteps = new HashSet<>();

    public Recipe(String title, String description, int preparationMinutes, Integer cookingMinutes, int servings, Difficulty difficulty, int estimatedCost) {
        this.title = title;
        this.description = description;
        this.preparationMinutes = preparationMinutes;
        this.readyInMinutes = preparationMinutes + cookingMinutes;
        this.cookingMinutes = cookingMinutes;
        this.servings = servings;
        this.difficulty = difficulty;
        this.estimatedCost = estimatedCost;
    }

    @PrePersist
    void onPersist() {
        if (active == null || !active.equals(Boolean.TRUE)) {
            active = false;
            publishedAt = null;
            updatedAt = null;
            deletedAt = Instant.now();
        }
    }

    @PreUpdate
    void onUpdate() {
        if (active.equals(Boolean.FALSE)) {
            active = false;
            updatedAt = Instant.now();
            deletedAt = Instant.now();
        }
        if (active.equals(Boolean.TRUE)) {
            active = true;
            updatedAt = Instant.now();
            publishedAt = Instant.now();
            deletedAt = null;
        }
    }

    public void addRecipeStep(RecipeStep step) {
        recipeSteps.add(step);
        step.setRecipe(this);
    }
    public void removeRecipeStep(RecipeStep step) {
        recipeSteps.remove(step);
        step.setRecipe(null);
    }
    public void addIngredientComposition(Ingredient ingredient, int amount, int order, String description, IngredientUnit unitOfMeasure) {
        this.ingredientCompositions.add(new RecipeIngredientComposition(this, ingredient, amount, order, description, unitOfMeasure));
    }
    public void removeIngredientComposition(Ingredient ingredient) {
        for (Iterator<RecipeIngredientComposition> iterator = ingredientCompositions.iterator();
             iterator.hasNext(); ) {
            RecipeIngredientComposition ingComp = iterator.next();
            if (ingComp.getRecipe().equals(this) && ingComp.getIngredient().equals(ingredient)) {
                iterator.remove();
                ingComp.setRecipe(null);
                ingComp.setIngredient(null);
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}