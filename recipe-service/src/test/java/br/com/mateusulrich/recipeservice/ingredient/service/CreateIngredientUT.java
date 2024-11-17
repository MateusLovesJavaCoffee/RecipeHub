package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.common.exception.IngredientNameAlreadyExistsException;
import br.com.mateusulrich.recipeservice.common.exception.MissingIdentifiersException;
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
import java.util.Objects;
import java.util.Set;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class CreateIngredientUT extends ServiceUnitTests {

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
    void givenAValidInput_whenCallsCreateIngredient_shouldReturnIngredientResponse() {
        final String expectName = "Tomato";
        final Set<UnitOfMeasure> expectUnits = Set.of(new UnitOfMeasure(1, "teste1"), new UnitOfMeasure(2, "teste2"));
        final String expectDescription = "Tomato";
        final String expectImgUrl = null;
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
        final Set<Integer> units = Set.of(1, 2);
        final Ingredient input = new Ingredient(expectName, expectDescription, expectCategory);

        when(ingredientRepository.existsByName(expectName)).thenReturn(false);
        when(unitRepo.findAllByIdIn(units)).thenReturn(expectUnits);
        when(ingredientRepository.save(any())).thenAnswer(returnsFirstArg());
        Ingredient response = ingredientService.save(units, input);

        Assertions.assertNotNull(response);
        Assertions.assertNull(response.getId());

        Mockito.verify(ingredientRepository, times(1)).save(argThat(ingredient ->
                Objects.equals(expectName, ingredient.getName())
                        && Objects.equals(expectDescription, ingredient.getDescription())
                        && Objects.equals(expectImgUrl, ingredient.getImageUrl())
                        && Objects.equals(expectCategory, ingredient.getCategory())
                        && Objects.equals(expectUnits, ingredient.getPossibleUnits())
                        && Objects.isNull(ingredient.getId())
        ));
        verify(unitRepo, times(1)).findAllByIdIn(units);
    }

    @Test
    void givenANonUniqueIngredientName_whenCallsCreateIngredient_shouldReturnException() {
        final String nonUniqueName = "Tomato";
        final String expectDescription = "Tomato";
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
        final Set<Integer> units = Set.of(1, 2, 3);

        final String expectErrorMessage = "The ingredient name 'Tomato' already exists.";

        final Ingredient input = new Ingredient(nonUniqueName, expectDescription, expectCategory);

        when(ingredientRepository.existsByName(nonUniqueName)).thenReturn(true);

        final var exception = Assertions.assertThrows(IngredientNameAlreadyExistsException.class, () -> {
            ingredientService.save(units, input);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(expectErrorMessage, exception.getMessage());

        Mockito.verify(ingredientRepository, times(1)).existsByName(any());
        Mockito.verify(ingredientRepository, times(0)).save(any());
    }

    @Test
    void givenANonExistingIngredientUnitID_whenCallsCreateIngredient_shouldReturnException() {
        final String expectName = "Tomato";
        final String expectDescription = "Tomato";
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
        final Set<Integer> nonExistingIds = Set.of(1, 2, 3);

        final String expectErrorMessage = "Some IngredientUnits could not be found: 1, 2, 3";

        final Ingredient input = new Ingredient(expectName, expectDescription, expectCategory);

        when(ingredientRepository.existsByName(expectName)).thenReturn(false);
        when(unitRepo.findAllByIdIn(nonExistingIds)).thenReturn(Set.of());

        final var exception = Assertions.assertThrows(MissingIdentifiersException.class, () -> {
            ingredientService.save(nonExistingIds, input);
        });

        Assertions.assertNotNull(exception);
        Assertions.assertEquals(expectErrorMessage, exception.getMessage());

        Mockito.verify(ingredientRepository, times(1)).existsByName(any());
        Mockito.verify(unitRepo, times(1)).findAllByIdIn(any());
        Mockito.verify(ingredientRepository, times(0)).save(any());
    }
}
