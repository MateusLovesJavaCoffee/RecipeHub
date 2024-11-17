package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeInstructionRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.InstructionResponse;
public interface RecipeInstructionService {

    InstructionResponse create(Integer recipeId, RecipeInstructionRequest input);
    void update(Integer recipeId, RecipeInstructionRequest input);
    void delete(Integer recipeId, Integer instructionId);
}
