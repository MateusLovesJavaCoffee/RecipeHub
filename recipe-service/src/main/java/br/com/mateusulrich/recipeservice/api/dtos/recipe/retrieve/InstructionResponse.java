package br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve;

import br.com.mateusulrich.recipeservice.recipe.entity.Instruction;

public record InstructionResponse(
        Integer recipeId,
        int instructionNumber,
        String description
) {

    public static InstructionResponse from(Instruction instruction) {
        return new InstructionResponse(
                instruction.getInstructionID().getRecipeId(),
                instruction.getInstructionID().getInstructionNumberId(),
                instruction.getDescription()
        );

    }

}
