package br.com.mateusulrich.recipeservice.ingredient.mapper;

import br.com.mateusulrich.recipeservice.ingredient.dtos.CreateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IngredientMapper {

    IngredientResponse toIngredientResponse(Ingredient ingredient);
    Ingredient fromDtoToEntity(CreateIngredientData data);
}
