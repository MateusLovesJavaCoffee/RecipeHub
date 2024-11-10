package br.com.mateusulrich.recipeservice.ingredient.repository;

import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Long> {

    @Query(value = "select i.id from IngredientUnit i where i.id in :ids")
    List<Long> getAllIdsByIds(@Param("ids") Iterable<Long> ids);

    @Query(value = "select i from IngredientUnit i where i.id in :ids")
    Set<IngredientUnit> findAllByIdIn(@Param("ids") Iterable<Long> ids);
}