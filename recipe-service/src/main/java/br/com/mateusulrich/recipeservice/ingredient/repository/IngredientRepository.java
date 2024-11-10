package br.com.mateusulrich.recipeservice.ingredient.repository;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

    Page<Ingredient> findAll(Specification<Ingredient> whereClause, Pageable page);

    @Query("select i from Ingredient i join fetch i.possibleUnits where i.id = :id")
    Optional<Ingredient> findIngredientWithUnits(@Param("id") Long id);

    boolean existsByName(@Param("name") String name);
}