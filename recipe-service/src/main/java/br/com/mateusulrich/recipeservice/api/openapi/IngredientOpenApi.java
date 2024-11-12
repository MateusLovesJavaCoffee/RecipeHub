package br.com.mateusulrich.recipeservice.api.openapi;

import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Ingredient", description = "A API de Ingredientes permite a criação, leitura, atualização e exclusão de ingredientes utilizados em receitas.")
public interface IngredientOpenApi {

    @Operation(
            summary = "Create a new Ingredient",
            description = "Cria um novo ingrediente com os dados fornecidos no corpo da requisição."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ingrediente criado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> addIngredient(IngredientInputData request);

    @Operation(
            summary = "Update an existing Ingredient by its identifier",
            description = "Atualiza as informações de um ingrediente existente, identificando-o pelo seu ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente atualizado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> updateIngredient(Long id, IngredientInputData request);

//    @Operation(
//            summary = "Update the photo of an Ingredient by its identifier",
//            description = "Atualiza a foto de um ingrediente, associando a imagem fornecida ao ingrediente identificado pelo ID."
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Foto do ingrediente atualizada com sucesso"),
//            @ApiResponse(responseCode = "400", description = "Requisição malformada"),
//            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
//            @ApiResponse(responseCode = "422", description = "Erro de validação"),
//            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
//    })
//    ResponseEntity<Void> updateIngredientPhoto(
//            @Parameter(description = "ID do ingrediente", required = true) @PathVariable Long ingredientId,
//            @Parameter(description = "Arquivo da foto do ingrediente", required = true) @RequestPart MultipartFile file
//    );

    @Operation(
            summary = "Delete an Ingredient by its identifier",
            description = "Exclui o ingrediente especificado pelo seu ID. Não retorna conteúdo, apenas um status de sucesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ingrediente excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Void> deleteIngredientById(
            @Parameter(description = "ID do ingrediente", required = true) @PathVariable Long ingredientId
    );

    @Operation(
            summary = "Get Ingredient by its identifier",
            description = "Recupera um ingrediente pelo seu identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingrediente recuperado com sucesso", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = IngredientResponse.class))),
            @ApiResponse(responseCode = "404", description = "Ingrediente não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<IngredientResponse> findIngredientById(
            @Parameter(description = "ID do ingrediente", required = true) @PathVariable Long ingredientId
    );

    @GetMapping
    @Operation(
            summary = "List all ingredients paginated",
            description = "Lista todos os ingredientes, com suporte a paginação, ordenação e limite de itens por página."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ingredientes recuperada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    ResponseEntity<Page<IngredientResponse>> listAllIngredients(
            SpecificationTemplate.IngredientSpec spec,
            @Parameter(hidden = true)Pageable pageable
    );

}
