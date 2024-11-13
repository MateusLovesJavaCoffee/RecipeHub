package br.com.mateusulrich.recipeservice.ingredient.repository;

import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UnitOfMeasureRepository extends JpaRepository<UnitOfMeasure, Integer> {

    @Query(value = "select i.id from UnitOfMeasure i where i.id in :ids")
    List<Long> getAllIdsByIds(@Param("ids") Iterable<Integer> ids);

    @Query(value = "select i from UnitOfMeasure i where i.id in :ids")
    Set<UnitOfMeasure> findAllByIdIn(@Param("ids") Iterable<Integer> ids);
}