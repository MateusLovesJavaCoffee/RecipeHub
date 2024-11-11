package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientDto;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.CreateRecipeData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.UpdateRecipeData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

;

@Tag(name = "Recipe", description = "A API de Receitas permite a criação, consulta, atualização e exclusão de ingredientes utilizados em receitas.")
public interface RecipeOpenApi {

    @Operation(
            summary = "Criando uma Nova Receita",
            description = "Cria uma nova Receita com os dados fornecidos no corpo da requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente criado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<RecipeResponse> createRecipe(CreateRecipeData data);

    @Operation(
            summary = "Update an existing Ingredient by its identifier",
            description = "Atualiza de forma parcial as informações de uma receita, identificando-o pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<RecipeResponse> updateRecipe(Long id, UpdateRecipeData data);


    @Operation(
            summary = "Deleta uma receita pelo seu ID.",
            description = "Exclui a receita especificada pelo seu ID. Não retorna conteúdo, apenas um status de sucesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteRecipeById(
            @Parameter(description = "ID da Receita", required = true) @PathVariable Long recipeId
    );

    @Operation(
            summary = "Busca uma receita pelo seu ID.",
            description = "Busca uma receita pelo seu Identificador Único"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente recuperado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> findRecipeById(
            @Parameter(description = "ID da Receita", required = true) @PathVariable Long ingredientId
    );

    @GetMapping
    @Operation(
            summary = "Listar todas as Receitas Paginada.",
            description = "Lista todos as Receitas, com suporte a paginação, ordenação, limite de itens por página, e filtragem;"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<IngredientResponse>> listAllRecipes(
            @Parameter(hidden = true)Pageable pageable
    );
}
