package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.api.dtos.photo.ImageMediaRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

@Tag(name = "Media")
public interface MediaFileOpenApi {

    @Operation(summary = "Atualizar a Foto do Ingrediente.", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do Ingrediente atualizada com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado.", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "500", description = "Erro interno do Servidor", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    void updateIngredientPhoto(
            @Parameter(description = "ID do ingrediente", example = "1", required = true) Integer ingredientId,
            @Parameter(description = "Arquivo de Imagem") ImageMediaRequest input) throws IOException;


    @Operation(summary = "Atualizar a Foto da Receita.", responses = {
            @ApiResponse(responseCode = "204", description = "Foto da Receita atualizada com Sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrado.", content = {
                    @Content(schema = @Schema(ref = "Problema")) }),
            @ApiResponse(responseCode = "500", description = "Erro interno do Servidor", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    void updateRecipePhoto(
            @Parameter(description = "ID da Receita", example = "1", required = true) Integer recipeId,
            @Parameter(description = "Arquivo de Imagem") ImageMediaRequest input) throws IOException;
}
