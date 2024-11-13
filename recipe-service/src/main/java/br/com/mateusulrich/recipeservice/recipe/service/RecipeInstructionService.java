package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.InstructionResponse;
public interface RecipeInstructionService {

    InstructionResponse create(Integer recipeId, RecipeInstructionInput input);
    void update(Integer recipeId, RecipeInstructionInput input);
    void delete(Integer recipeId, Integer instructionId);
}
