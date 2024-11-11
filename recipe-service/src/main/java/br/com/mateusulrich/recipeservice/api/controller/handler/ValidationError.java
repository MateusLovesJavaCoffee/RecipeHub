package br.com.mateusulrich.recipeservice.api.controller.handler;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ValidationErrors", description = "Represents a validation error for a specific field")
public record ValidationError(

        @Schema(example = "name", description = "The name of the field that caused the error")
        String field,
        @Schema(example = "name is required", description = "The error message associated with the field")
        String message
) {

}
