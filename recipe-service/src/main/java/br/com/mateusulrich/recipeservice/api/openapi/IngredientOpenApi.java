package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Tag(name = "Ingredientes", description = "A API de Ingredientes permite a criação, leitura, atualização e exclusão de ingredientes utilizados em receitas.")
public interface IngredientOpenApi {

    @Operation(
            summary = "Create um novo Ingrediente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> createIngredient(
            @RequestBody(description = "Representação dos dados para criar um novo Ingrediente", required = true)
            IngredientRequest request);
    @Parameter(description = "ID da Receita", required = true)
    @RequestBody(description = "Representação dos dados para inserir um ingrediente na receita", required = true)

    @Operation(summary = "Atualizar um Ingrediente pelo seu ID" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> updateIngredient(
            @Parameter(description = "ID do Ingrediente", required = true) Integer id,
            @RequestBody(description = "Representação dos dados para atualizar um Ingrediente", required = true)
            IngredientRequest request);

    @Operation(summary = "Deletar um Ingrediente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "409", description = "O ingrediente não pode ser deletado, pois está em uso."),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteIngredient(
            @Parameter(description = "ID do ingrediente", required = true) @PathVariable Integer ingredientId
    );

    @Operation(
            summary = "Buscar um Ingrediente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente recuperado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> findIngredient(
            @Parameter(description = "ID do Ingrediente", required = true) @PathVariable Integer ingredientId
    );

    @GetMapping
    @Operation(summary = "Listar todos os Ingredientes Paginado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingredientes recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<IngredientResponse>> listAllIngredients(
            @Parameter(description = "Nome da Categoria") String category,
            @Parameter(description = "Nome do Ingrediente") String name,

            @Parameter(
                    description = "Número da página a ser retornada (padrão é 0). Exemplo: 1, 2, 3...",
                    example = "0"
            )  int page,

            @Parameter(
                    description = "Número de itens por página. Exemplo: 10, 20, 50...",
                    example = "20"
            ) int size,

            @Parameter(
                    description = "Critério de ordenação. Exemplo: 'name' para ordenar pelo nome , ou 'id' para ordenar pelo id",
                    example = "id"
            ) String sort,
            @Parameter(
                    description = "Direção da ordenção,'asc' para Crescente e 'desc' para Decrescente",
                    example = "desc"
            ) String direction
    );


}
