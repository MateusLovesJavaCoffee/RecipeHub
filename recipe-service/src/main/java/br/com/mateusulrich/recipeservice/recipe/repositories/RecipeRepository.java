package br.com.mateusulrich.recipeservice.recipe.repositories;

import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer>, JpaSpecificationExecutor<Recipe> {

    @Query(value = """
        select r
        from Recipe r
        left join fetch r.instructions
        left join fetch r.ingredientCompositions ic
        left join fetch ic.ingredient
        where r.id = :id
""")
    Optional<Recipe> findById(@Param("id") Long id);

}
