package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
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

class FindIngredientByIdTest extends ServiceUnitTests {

    @InjectMocks
    private IngredientServiceImpl ingredientService;

    @Mock
    private IngredientUnitRepository unitRepo;

    @Mock
    private IngredientRepository ingredientRepository;

    @Override
    protected List<Object> getMocks() {
        return List.of(unitRepo, ingredientRepository);
    }

    @Test
    void givenAValidID_whenCallsFindIngredientById_thenReturnIngredientResponse() {
        final Long expectId = 10L;
        final String expectName = "Tomato";
        final Set<IngredientUnit> expectUnits = Set.of(new IngredientUnit(1L, "teste1"), new IngredientUnit(2L, "teste2"), new IngredientUnit(3L, "teste3"));
        final String expectDescription = "Tomato";
        final String expectImgUrl = "https://via.placeholder.com/150";
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;

        Ingredient ingredient = new Ingredient(expectName, expectDescription, expectCategory, expectUnits);
        ingredient.setId(expectId);
        ingredient.setImageUrl(expectImgUrl);

        when(ingredientRepository.findById(expectId)).thenReturn(Optional.of(ingredient));

        final var output = ingredientService.findIngredientById(expectId);

        Assertions.assertEquals(expectId, output.id());
        Assertions.assertEquals(expectName, output.name());
        Assertions.assertEquals(expectDescription, output.shortDescription());
        Assertions.assertEquals(expectImgUrl, output.imageUrl());
        Assertions.assertEquals(expectCategory, output.category());

        Mockito.verify(ingredientRepository, times(1)).findById(expectId);
    }

    @Test
    void givenAValidId_whenCallsFindIngredientByIdAndDoesNotNotExist_thenReturnReturnNotFoundException() {
        final var expectedErrorMessage = "Ingredient with ID: 10 was not found.";

        final var expectedId = 10L;

        when(ingredientRepository.findById(expectedId))
                .thenReturn(Optional.empty());

        final var exception = Assertions.assertThrows(NotFoundException.class, () -> {
            ingredientService.findIngredientById(expectedId);
        });

        Assertions.assertEquals(expectedErrorMessage, exception.getMessage());
        Mockito.verify(ingredientRepository, times(1)).findById(expectedId);
    }
}
