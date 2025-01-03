package br.com.mateusulrich.recipeservice.recipe.entity;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeRequest;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

@Getter
@Entity
@AllArgsConstructor
@Table(name = "recipes")
@BatchSize(size = 20)
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Setter
    @Column(name = "img_url", length = 250)
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
    private int servings = 0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Difficulty difficulty;

    @Column(name = "estimated_cost", nullable = false)
    private int estimatedCost = 0;

    @Column(name = "likes", nullable = false)
    private int likes = 0;

    @Setter
    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set<RecipeIngredient> recipeIngredients = new HashSet<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Instruction> instructions = new HashSet<>();

    public Recipe() {
        this.active = false;
        this.deletedAt = Instant.now();
    }

    public Recipe(String title, String description, int preparationMinutes, Integer cookingMinutes, int servings, Difficulty difficulty, int estimatedCost) {
        this.title = title;
        this.description = description;
        this.preparationMinutes = preparationMinutes;
        this.cookingMinutes = cookingMinutes;
        this.servings = servings;
        this.difficulty = difficulty;
        this.estimatedCost = estimatedCost;
        this.setActive(false);
        this.deletedAt = Instant.now();
    }

    @PrePersist @PreUpdate
    void onPersist() {
        this.updatedAt = Instant.now();
        this.setReadyInMinutes(this.cookingMinutes + this.preparationMinutes);
    }

    public void update(RecipeRequest input) {
        this.title = input.getTitle();
        this.description = input.getDescription();
        this.preparationMinutes = input.getPreparationMinutes();
        this.cookingMinutes = input.getCookingMinutes();
        this.servings = input.getServings();
        this.difficulty = input.getDifficulty();
        this.estimatedCost = input.getEstimatedCost();
    }

    public void addInstruction(Instruction instruction) {
        instructions.add(instruction);
        instruction.setRecipe(this);
    }
    public void removeRecipeStep(Instruction instruction) {
        instructions.remove(instruction);
        instruction.setRecipe(null);
    }
    public void addIngredientComposition(Ingredient ingredient, int amount, int order, String description, UnitOfMeasure unitOfMeasure) {
        this.recipeIngredients.add(new RecipeIngredient(this, ingredient, amount, order, description, unitOfMeasure));
    }
    public void removeIngredientComposition(Ingredient ingredient) {
        for (Iterator<RecipeIngredient> iterator = recipeIngredients.iterator();
             iterator.hasNext(); ) {
            RecipeIngredient ingComp = iterator.next();
            if (ingComp.getRecipe().equals(this) && ingComp.getIngredient().equals(ingredient)) {
                iterator.remove();
                ingComp.setRecipe(null);
                ingComp.setIngredient(null);
            }
        }
    }
    public void changeStatus(Boolean status) {
        if (Boolean.TRUE.equals(status) && this.getPublishedAt() == null) {
            this.publishedAt = Instant.now();
            this.deletedAt = null;
            return;
        }
        if (Boolean.FALSE.equals(status)) {
            this.updatedAt = Instant.now();
            this.deletedAt = Instant.now();
            this.setActive(false);
            return;
        }
        this.setActive(status);
        this.deletedAt = null;
        this.updatedAt = Instant.now();
    }

    public Recipe setEstimatedCost(int estimatedCost) {
        this.estimatedCost = estimatedCost;
        return this;
    }

    public Recipe setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public Recipe setServings(int servings) {
        this.servings = servings;
        return this;
    }

    public Recipe setCookingMinutes(Integer cookingMinutes) {
        this.cookingMinutes = cookingMinutes;
        return this;
    }

    public Recipe setReadyInMinutes(int readyInMinutes) {
        this.readyInMinutes = readyInMinutes;
        return this;
    }

    public Recipe setPreparationMinutes(int preparationMinutes) {
        this.preparationMinutes = preparationMinutes;
        return this;
    }

    public Recipe setDescription(String description) {
        this.description = description;
        return this;
    }

    public Recipe setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public Recipe setTitle(String title) {
        this.title = title;
        return this;
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