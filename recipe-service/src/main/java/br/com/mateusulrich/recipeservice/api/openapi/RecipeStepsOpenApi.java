package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeStepInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Recipe Steps", description = "A API de passos de uma Receita permite a adição, edição e remoção dos ingredientes utilizados na receita.")
public interface RecipeStepsOpenApi {


    @Operation(summary = "Adicionar um passo a Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> addStepIntoRecipe(Long recipeId, RecipeStepInput input);

    @Operation(
            summary = "Atualizar um Ingrediente na Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "404", description = "Unidade de medida não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> updateIngredientIntoRecipe(
            Long recipeId,
            IngredientCompositionInput input);

    @Operation(
            summary = "Deleta um Ingrediente na Receita pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteIngredientIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Long recipeId,
            @Parameter(description = "ID do Ingrediente", required = true) Long ingredientId
    );
}
