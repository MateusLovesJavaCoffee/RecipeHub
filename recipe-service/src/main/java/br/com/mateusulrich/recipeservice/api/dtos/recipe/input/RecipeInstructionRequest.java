package br.com.mateusulrich.recipeservice.api.dtos.recipe.input;

import io.swagger.v3.oas.annotations.media.Schema;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public record RecipeInstructionRequest(

        @Schema(
                description = "Número da instrução na sequência da receita. Exemplo: 1 para a primeira instrução, 2 para a segunda, e assim por diante."
        )
        @NotNull
        @Min(1)
        @Max(99)
        Integer instructionNumber,

        @Schema(
                description = "Descrição detalhada da instrução de preparo da receita. Deve conter informações claras sobre como realizar a etapa de preparo.",
                example = "Misture todos os ingredientes secos em uma tigela grande."
        )
        @NotBlank
        @Length(min = 1, max = 250)
        String description

) implements Serializable {
}
