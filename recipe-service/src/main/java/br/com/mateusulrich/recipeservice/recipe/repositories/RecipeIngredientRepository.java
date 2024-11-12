package br.com.mateusulrich.recipeservice.recipe.repositories;

import br.com.mateusulrich.recipeservice.recipe.jpa.RecipeIngredientComposition;
import br.com.mateusulrich.recipeservice.recipe.jpa.RecipeIngredientsID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredientComposition, RecipeIngredientsID> {

    @Query(value = "select ric from RecipeIngredientComposition ric where ric.id.ingredientId = :ingredientId and ric.id.recipeId = :recipeId")
    Optional<RecipeIngredientComposition> findByRecipeAndIngredient(Long recipeId, Long ingredientId);
}