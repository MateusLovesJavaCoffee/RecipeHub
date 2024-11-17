package br.com.mateusulrich.recipeservice.recipe.repositories;

import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.repositories.projections.ListRecipesProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {


    @Override
    @Query(value = """
        select r
        from Recipe r
        left join fetch r.instructions
        left join fetch r.recipeIngredients ic
        left join fetch ic.ingredient
        left join fetch ic.unitOfMeasure
        where r.id = :id
""")
    Optional<Recipe> findById(@Param("id") Integer id);

    @Query(value = "SELECT r.id AS id, " +
            "r.title AS title, " +
            "r.img_url AS imageUrl, " +
            "r.likes AS likes, " +
            "r.ready_in_minutes AS readyInMinutes, " +
            "r.difficulty AS difficulty, " +
            "COUNT(CASE WHEN i.id IN :ingredientIds THEN 1 END) AS ingredientMatchCount " +
            "FROM recipes r " +
            "JOIN recipe_ingredients ri ON r.id = ri.recipe_id " +
            "JOIN ingredients i ON ri.ingredient_id = i.id " +
            "WHERE i.id IN :ingredientIds " +
            "GROUP BY r.id, r.title, r.likes " +
            "ORDER BY ingredientMatchCount DESC, r.likes DESC",
            countQuery = "SELECT COUNT(*) FROM recipes r " +
                    "JOIN recipe_ingredients ri ON r.id = ri.recipe_id " +
                    "JOIN ingredients i ON ri.ingredient_id = i.id " +
                    "WHERE i.id IN :ingredientIds",
            nativeQuery = true)
    Page<ListRecipesProjection> listRecipesMatchIngredients(@Param("ingredientIds") Set<Integer> ingredientIds, Pageable pageable);


}
