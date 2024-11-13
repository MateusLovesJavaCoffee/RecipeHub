package br.com.mateusulrich.recipeservice.recipe.repositories;

import br.com.mateusulrich.recipeservice.recipe.entity.IngredientComposition;
import br.com.mateusulrich.recipeservice.recipe.entity.RecipeIngredientsID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientCompositionRepository extends JpaRepository<IngredientComposition, RecipeIngredientsID> {

    @Query(value = "select ric from IngredientComposition ric where ric.id.ingredientId = :ingredientId and ric.id.recipeId = :recipeId")
    IngredientComposition findByRecipeAndIngredient(Integer recipeId, Integer ingredientId);

    @Query(value = "SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM IngredientComposition c WHERE c.id.recipeId = :recipeId and c.id.ingredientId = :ingredientId")
    boolean existsIngredientIntoRecipe(@Param("recipeId") Integer recipeId, @Param("ingredientId") Integer ingredientId);

}