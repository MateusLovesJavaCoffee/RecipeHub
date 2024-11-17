package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientResponse;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeInstructionRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.InstructionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Receita Instrução", description = "A API de instruções de Receita permite a adição, edição e remoção de instruções utilizadas na receita.")
public interface RecipeInstructionsOpenApi {

    @Operation(summary = "Adicionar uma Instrução passo a passo na Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Instrução criada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<InstructionResponse> createInstructionIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @RequestBody(description = "Representação dos dados para inserir uma instrução na receita", required = true)RecipeInstructionRequest input);

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
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @RequestBody(description = "Representação dos dados para inserir um ingrediente na receita", required = true) RecipeInstructionRequest input);

    @Operation(summary = "Deleta uma Instrução na Receita pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Instrução de receita excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Passo não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteInstructionIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @Parameter(description = "ID da Instrução", required = true) Integer instructionId
    );
}
