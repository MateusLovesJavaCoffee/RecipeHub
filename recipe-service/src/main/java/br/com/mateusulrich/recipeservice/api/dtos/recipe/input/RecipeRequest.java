package br.com.mateusulrich.recipeservice.api.dtos.recipe.input;

import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.enums.Difficulty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
public class RecipeRequest {

        @Schema(
                description = "Título da receita.",
                example = "Bolo de Chocolate"
        )
        @NotBlank
        @Length(min = 1, max = 100)
        private String title;

        @Schema(
                description = "Tempo de preparo em minutos.",
                example = "30"
        )
        @NotNull
        @Min(value = 1)
        @Max(value = 999)
        private Integer preparationMinutes;

        @Schema(
                description = "Tempo de cozimento em minutos.",
                example = "45"
        )
        @PositiveOrZero
        @Max(value = 999)
        private Integer cookingMinutes;

        @Schema(
                description = "Descrição detalhada da receita.",
                example = "Esta receita de bolo de chocolate é simples e deliciosa, perfeita para qualquer ocasião."
        )
        @Length(min = 1, max = 1000)
        private String description;

        @Schema(
                description = "Número de porções que a receita rende.",
                example = "8"
        )
        @NotNull
        @Min(value = 1)
        @Max(value = 99)
        private Integer servings;

        @Schema(
                description = "Dificuldade da receita.",
                example = "EASY"
        )
        private Difficulty difficulty;

        @Schema(
                description = "Custo estimado da receita em número inteiro",
                example = "15"
        )
        @NotNull
        @Min(value = 1)
        @Max(value = 999)
        private Integer estimatedCost;

        public RecipeRequest(String title, Integer preparationMinutes, Integer cookingMinutes, String description, Integer servings, Difficulty difficulty, Integer estimatedCost) {
                this.title = title;
                this.preparationMinutes = preparationMinutes;
                this.cookingMinutes = cookingMinutes;
                this.description = description;
                this.servings = servings;
                this.difficulty = difficulty;
                this.estimatedCost = estimatedCost;
        }

        public Recipe toEntity() {
                return new Recipe(
                        this.title, this.description, this.preparationMinutes, this.cookingMinutes == null ? 0 : cookingMinutes, this.servings, this.difficulty, this.estimatedCost
                );
        }

        public Integer getCookingMinutes() {
                return this.cookingMinutes == null ? 0 : this.cookingMinutes;
        }
}
