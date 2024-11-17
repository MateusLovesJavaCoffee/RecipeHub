package br.com.mateusulrich.recipeservice.recipe.repositories;

import br.com.mateusulrich.recipeservice.recipe.entity.Instruction;
import br.com.mateusulrich.recipeservice.recipe.entity.InstructionID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeInstructionRepository extends JpaRepository<Instruction, InstructionID> {
}