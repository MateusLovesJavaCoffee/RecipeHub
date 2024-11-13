package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.common.exception.DomainException;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.InstructionResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Instruction;
import br.com.mateusulrich.recipeservice.recipe.entity.InstructionID;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.repositories.InstructionRepository;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeInstructionServiceImpl implements RecipeInstructionService {

    private final InstructionRepository repository;
    private final RecipeServiceImpl recipeService;
    private final RecipeRepository recipeRepository;
    private final InstructionRepository instructionRepository;

    @Override
    @Transactional
    public InstructionResponse create(Integer recipeId, RecipeInstructionInput input) {
        return InstructionResponse.from(save(recipeId, input));
    }
    @Override
    @Transactional
    public void update(Integer recipeId, RecipeInstructionInput input) {
        save(recipeId, input);
    }

    private Instruction save(Integer recipeId, RecipeInstructionInput input) {
        Recipe recipe = recipeService.findOrElseThrow(recipeId);
        InstructionID instructionID = new InstructionID(input.instructionNumber(), recipeId);
        Optional<Instruction> existingInstruction = repository.findById(instructionID);

        if (existingInstruction.isPresent()) {
            existingInstruction.get().setDescription(input.description());
            return instructionRepository.save(existingInstruction.get());
        }
        else {
            Instruction instruction = new Instruction(recipe, input.instructionNumber(), input.description());
            recipe.addInstruction(instruction);
            recipeRepository.save(recipe);
            return instruction;
        }
    }

    @Override
    @Transactional
    public void delete(Integer recipeId, Integer instructionId) {
        final var id = new InstructionID(instructionId, recipeId);
        if (instructionRepository.existsById(id)) {
            try {
                instructionRepository.deleteById(id);
            }catch (Exception ex) {
                throw new DomainException(ex.getMessage(), ex.getCause());
            }

        }
    }
}
