package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class FindIngredientByIdUT extends ServiceUnitTests {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private UnitOfMeasureRepository unitRepo;

    @Mock
    private IngredientRepository ingredientRepository;

    @Override
    protected List<Object> getMocks() {
        return List.of(unitRepo, ingredientRepository);
    }

    @Test
    void givenAValidID_whenCallsFindIngredientById_thenReturnIngredientResponse() {
        final Integer expectId = 10;
        final String expectName = "Tomato";
        final Set<UnitOfMeasure> expectUnits = Set.of(new UnitOfMeasure(1, "teste1"), new UnitOfMeasure(2, "teste2"), new UnitOfMeasure(3, "teste3"));
        final String expectDescription = "Tomato";
        final String expectImgUrl = "https://via.placeholder.com/150";
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;

        Ingredient ingredient = new Ingredient(expectName, expectDescription, expectCategory, expectUnits);
        ingredient.setId(expectId);
        ingredient.setImageUrl(expectImgUrl);

        when(ingredientRepository.findById(expectId)).thenReturn(Optional.of(ingredient));

        final var output = ingredientService.findOrThrowNotFound(expectId);

        Assertions.assertEquals(expectId, output.getId());
        Assertions.assertEquals(expectName, output.getName());
        Assertions.assertEquals(expectDescription, output.getDescription());
        Assertions.assertEquals(expectImgUrl, output.getImageUrl());
        Assertions.assertEquals(expectCategory, output.getCategory());

        Mockito.verify(ingredientRepository, times(1)).findById(expectId);
    }

    @Test
    void givenAValidId_whenCallsFindIngredientByIdAndDoesNotNotExist_thenReturnReturnNotFoundException() {
        final var expectedErrorMessage = "Ingredient with ID: 10 was not found.";

        final var expectedId = 10;

        when(ingredientRepository.findById(expectedId))
                .thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(NotFoundException.class, () -> {
            ingredientService.findOrThrowNotFound(expectedId);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Mockito.verify(ingredientRepository, times(1)).findById(expectedId);
    }
}
