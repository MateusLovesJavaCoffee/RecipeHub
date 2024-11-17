package br.com.mateusulrich.recipeservice.api.dtos.recipe.input;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record RecipeIngredientRequest(

        @Schema(
                description = "ID do ingrediente relacionado à receita",
                example = "1"
        )
        @NotNull
        Integer ingredientId,

        @Schema(
                description = "ID da unidade de medida do ingrediente (ex: 'kg', 'g', 'unidade').",
                example = "2"
        )
        @NotNull
        Integer unitOfMeasureId,

        @Schema(
                description = "Quantidade do ingrediente na unidade de medida especificada.",
                example = "250"
        )
        @NotNull
        @Min(1)
        @Max(9999)
        Integer amount,

        @Schema(
                description = "Ordem de apresentação dos ingredientes na receita (ex: ordem em que o ingrediente é adicionado).",
                example = "1"
        )
        @NotNull
        @Min(1)
        @Max(99)
        Integer order,

        @Schema(
                description = "Descrição opcional do ingrediente, como observações adicionais sobre o uso ou preparo.",
                example = "Desfiado, usado para incrementar o sabor"
        )
        @Length(max = 50)
        String description

) implements Serializable {

}
