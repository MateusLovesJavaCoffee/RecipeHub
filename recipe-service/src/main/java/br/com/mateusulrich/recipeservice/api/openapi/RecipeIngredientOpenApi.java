package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.UpdateRecipeIngredientRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Receita Ingredientes", description = "A API de Ingredientes da Receita permite a adição, edição e remoção dos ingredientes utilizados na receita.")
public interface RecipeIngredientOpenApi {


    @Operation(
            summary = "Adicionar um Ingrediente a Receita"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> addIngredientIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @RequestBody(description = "Representação dos dados para inserir um ingrediente na receita", required = true)RecipeIngredientRequest input);

    @Operation(
            summary = "Atualizar um Ingrediente na Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente adicionado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "404", description = "Unidade de medida não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> updateIngredientIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @Parameter(description = "ID do Ingrediente", required = true) Integer ingredientId,
            @RequestBody(description = "Representação dos dados para atualizar um ingrediente na receita", required = true) UpdateRecipeIngredientRequest input);

    @Operation(
            summary = "Deleta um Ingrediente na Receita pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteIngredientIntoRecipe(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId,
            @Parameter(description = "ID do Ingrediente", required = true) Integer ingredientId
    );
}
