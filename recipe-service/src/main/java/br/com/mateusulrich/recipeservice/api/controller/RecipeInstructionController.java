package br.com.mateusulrich.recipeservice.api.controller;

import br.com.mateusulrich.recipeservice.api.openapi.RecipeInstructionsOpenApi;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeInstructionRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.InstructionResponse;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeInstructionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/recipes/{recipeId}/instructions")
@RequiredArgsConstructor
public class RecipeInstructionController implements RecipeInstructionsOpenApi {

    private final RecipeInstructionService recipeInstructionService;

    @Override
    @PostMapping
    public ResponseEntity<InstructionResponse> createInstructionIntoRecipe(@PathVariable Integer recipeId, @RequestBody @Valid RecipeInstructionRequest input) {
        InstructionResponse instruction = recipeInstructionService.create(recipeId,input);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(instruction.instructionNumber()).toUri();
        return ResponseEntity.created(uri).body(instruction);
    }

    @Override
    @PutMapping
    public ResponseEntity<Void> updateInstructionIntoRecipe(@PathVariable Integer recipeId, @RequestBody @Valid RecipeInstructionRequest input) {
        recipeInstructionService.update(recipeId,input);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping(value = "/{instructionId}")
    public ResponseEntity<Void> deleteInstructionIntoRecipe(@PathVariable Integer recipeId, @PathVariable Integer instructionId) {
        recipeInstructionService.delete(recipeId, instructionId);

        return ResponseEntity.noContent().build();
    }
}
