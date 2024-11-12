package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeInstructionsOpenApi;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.entity.Instruction;
import org.springframework.http.ResponseEntity;

public class RecipeInstructionController implements RecipeInstructionsOpenApi {
    @Override
    public ResponseEntity<Instruction> createInstructionIntoRecipe(Long recipeId, RecipeInstructionInput input) {
        return null;
    }

    @Override
    public ResponseEntity<Void> updateInstructionIntoRecipe(Long recipeId, RecipeInstructionInput input) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteInstructionIntoRecipe(Long instructionId) {
        return null;
    }
}
