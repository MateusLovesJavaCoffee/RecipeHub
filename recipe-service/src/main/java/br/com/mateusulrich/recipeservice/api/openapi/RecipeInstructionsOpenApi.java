package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInstructionInput;
import br.com.mateusulrich.recipeservice.recipe.entity.Instruction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recipe Instructions", description = "A API de passos de uma Receita permite a adição, edição e remoção de instruções utilizados na receita.")
public interface RecipeInstructionsOpenApi {


    @Operation(summary = "Adicionar uma Instrução passo a passo na Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrução criada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Instruction> createInstructionIntoRecipe(Long recipeId, RecipeInstructionInput input);

    @Operation(
            summary = "Atualizar uma Instrução na Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrução atualizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> updateInstructionIntoRecipe(
            Long recipeId,
            RecipeInstructionInput input);

    @Operation(summary = "Deleta uma Instrução na Receita pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Instrução de receita excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Passo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteInstructionIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Long instructionId
    );
}
