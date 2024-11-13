package br.com.mateusulrich.recipeservice.ingredient.repository;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer>, JpaSpecificationExecutor<Ingredient> {

    @Query("select i from Ingredient i left join fetch i.possibleUnits where i.id = :id")
    Optional<Ingredient> findIngredientWithUnits(@Param("id") Integer id);

    boolean existsByName(@Param("name") String name);
}