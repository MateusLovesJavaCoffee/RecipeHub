package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.ListRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.RecipeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Tag(name = "Receitas", description = "A API de Receitas permite a criação, consulta, atualização e exclusão de receitas, possibilitando gerenciar informações relacionadas aos pratos.")
public interface RecipeOpenApi {

    @Operation(
            summary = "Criação de uma nova receita"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Receita criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada. Dados inválidos ou ausentes."),
            @ApiResponse(responseCode = "422", description = "Erro de validação. Falta de informações ou dados inválidos."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    ResponseEntity<RecipeResponse> createRecipe(RecipeInput data);

    @Operation(
            summary = "Atualização de uma receita existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada. Dados inválidos ou ausentes."),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada. ID inválido."),
            @ApiResponse(responseCode = "422", description = "Erro de validação. Algum dado não é válido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    ResponseEntity<RecipeResponse> updateRecipe(Integer id, RecipeInput data);

    @Operation(summary = "Ativa uma Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status da entidade atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> activate(
            @Parameter(description = "ID da entidade", required = true)Integer id
    );

    @Operation(summary = "Desativa uma Receita")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita desativada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entidade não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deactivate(
            @Parameter(description = "ID da entidade", required = true)Integer id
    );

    @Operation(
            summary = "Exclusão de uma receita pelo ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Receita excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada. ID inválido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    ResponseEntity<Void> deleteRecipeById(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId
    );

    @Operation(
            summary = "Busca uma receita pelo seu ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Receita recuperada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Receita não encontrada. ID inválido."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor.")
    })
    ResponseEntity<GetRecipeResponse> findRecipeById(
            @Parameter(description = "ID da Receita", required = true) Integer recipeId
    );

    @GetMapping
    @Operation(summary = "Lista as receitas paginada por ingredientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de receitas recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<PageResponse<ListRecipeResponse>> listRecipesByIngredients(
            @Parameter(description = "IDS dos Ingredientes") Set<Integer> ids,
            @Parameter(hidden = true) Pageable pageable
    );
}
