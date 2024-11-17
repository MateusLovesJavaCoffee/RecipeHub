package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.common.exception.IngredientNameAlreadyExistsException;
import br.com.mateusulrich.recipeservice.common.exception.MissingIdentifiersException;
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

import java.util.*;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class UpdateIngredientUT extends ServiceUnitTests {

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
    void givenAValidInput_whenCallsUpdateIngredient_thenSuccess() {
        final var expectId = 10;
        final Ingredient toUpdate = new Ingredient("Tomateee", "Tomateee", IngredientCategory.BAKING, new HashSet<>());
        toUpdate.setId(expectId);

        final String expectName = "Tomato";
        final Set<UnitOfMeasure> expectUnits = Set.of(new UnitOfMeasure(1, "teste1"), new UnitOfMeasure(2, "teste2"));
        final String expectDescription = "Tomato";
        final String expectImgUrl = null;
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
        final Set<Integer> units =  new HashSet<>(Set.of(1, 2));

        final IngredientRequest input = new IngredientRequest(expectName, expectDescription, expectCategory, units);

        when(ingredientRepository.findById(10))
                .thenReturn(Optional.of(toUpdate));

        when(ingredientRepository.existsByName(expectName)).thenReturn(false);
        when(unitRepo.findAllByIdIn(units)).thenReturn(expectUnits);
        when(ingredientRepository.save(any())).thenAnswer(returnsFirstArg());
        ingredientService.update(expectId, input);

        Mockito.verify(ingredientRepository, times(1)).save(argThat(ingredient ->
                Objects.equals(expectName, ingredient.getName())
                        && Objects.equals(expectDescription, ingredient.getDescription())
                        && Objects.equals(expectImgUrl, ingredient.getImageUrl())
                        && Objects.equals(expectCategory, ingredient.getCategory())
                        && Objects.equals(expectUnits, ingredient.getPossibleUnits())
                        && Objects.equals(expectId, ingredient.getId())
        ));
        verify(unitRepo, times(1)).findAllByIdIn(units);
        verify(ingredientRepository, times(1)).existsByName(expectName);
        verify(ingredientRepository, times(1)).save(any());
    }
    @Test
    void givenSameName_whenCallsUpdateIngredient_thenAssertNameDoesNotExistIsSkipped() {
        final var expectId = 10;
        final String sameName = "Tomato";

        final Ingredient toUpdate = new Ingredient(sameName, "Tomateee", IngredientCategory.BAKING, new HashSet<>());
        toUpdate.setId(expectId);

        final Set<UnitOfMeasure> expectUnits = Set.of(new UnitOfMeasure(1, "teste1"), new UnitOfMeasure(2, "teste2"), new UnitOfMeasure(3, "teste3"));
        final String expectDescription = "Tomato";
        final String expectImgUrl = null;
        final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
        final Set<Integer> units =  new HashSet<>(Set.of(1, 2));

        final IngredientRequest input = new IngredientRequest(sameName, expectDescription, expectCategory, units);

        when(ingredientRepository.findById(10))
                .thenReturn(Optional.of(toUpdate));

        when(unitRepo.findAllByIdIn(units)).thenReturn(expectUnits);
        when(ingredientRepository.save(any())).thenAnswer(returnsFirstArg());
        ingredientService.update(expectId, input);

        Mockito.verify(ingredientRepository, times(1)).save(argThat(ingredient ->
                Objects.equals(sameName, ingredient.getName())
                        && Objects.equals(expectDescription, ingredient.getDescription())
                        && Objects.equals(expectImgUrl, ingredient.getImageUrl())
                        && Objects.equals(expectCategory, ingredient.getCategory())
                        && Objects.equals(expectUnits, ingredient.getPossibleUnits())
                        && Objects.equals(expectId, ingredient.getId())
        ));
        verify(unitRepo, times(1)).findAllByIdIn(units);
        verify(ingredientRepository, times(0)).existsByName(sameName);
        verify(ingredientRepository, times(1)).save(any());


    }

     @Test
     void givenANonUniqueName_whenCallsUpdateIngredient_thenReturnException() {
         final var expectId = 10;
         final Ingredient toUpdate = new Ingredient("Tomateee", "Tomateee", IngredientCategory.BAKING, new HashSet<>());
         toUpdate.setId(expectId);

         final String nonUniqueName = "Tomato";
         final Set<UnitOfMeasure> expectUnits = Set.of(new UnitOfMeasure(1, "teste1"), new UnitOfMeasure(2, "teste2"), new UnitOfMeasure(3, "teste3"));
         final String expectDescription = "Tomato";
         final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
         final Set<Integer> units =  new HashSet<>(Set.of(1, 2));

         final String expectErrorMsg = "The ingredient name 'Tomato' already exists.";

         final IngredientRequest input = new IngredientRequest(nonUniqueName, expectDescription, expectCategory, units);

         when(ingredientRepository.findById(10))
                 .thenReturn(Optional.of(toUpdate));

         when(ingredientRepository.existsByName(nonUniqueName)).thenReturn(true);
         when(unitRepo.findAllByIdIn(units)).thenReturn(expectUnits);

         final var exception = Assertions.assertThrows(IngredientNameAlreadyExistsException.class, () -> {
             ingredientService.update(expectId, input);
         });

         Assertions.assertNotNull(exception);
         Assertions.assertEquals(expectErrorMsg, exception.getMessage());

         verify(unitRepo, times(1)).findAllByIdIn(units);
         verify(ingredientRepository, times(1)).existsByName(nonUniqueName);
         verify(ingredientRepository, times(0)).save(any());
     }

     @Test
     void givenANonExistingIngredientUnitIDs_whenCallsUpdateIngredient_shouldReturnException() {
         final var expectId = 10;
         final Ingredient toUpdate = new Ingredient("Tomateee", "Tomateee", IngredientCategory.BAKING, new HashSet<>());
         toUpdate.setId(expectId);

         final String expectName = "Tomato";
        final String expectDescription = "Tomato";
         final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
         final Set<Integer> nonExistingIds =  new HashSet<>(Set.of(1, 2));

         final String expectErrorMsg = "Some IngredientUnits could not be found: 1, 2";

         final IngredientRequest input = new IngredientRequest(expectName, expectDescription, expectCategory, nonExistingIds);

         when(ingredientRepository.findById(10))
                 .thenReturn(Optional.of(toUpdate));
         when(unitRepo.findAllByIdIn(nonExistingIds)).thenReturn(Set.of());
         when(ingredientRepository.existsByName(expectName)).thenReturn(false);

         final var exception = Assertions.assertThrows(MissingIdentifiersException.class, () -> {
             ingredientService.update(expectId, input);
         });

         Assertions.assertNotNull(exception);
         Assertions.assertEquals(expectErrorMsg, exception.getMessage());

         verify(unitRepo, times(1)).findAllByIdIn(nonExistingIds);
         verify(ingredientRepository, times(1)).existsByName(expectName);
         verify(ingredientRepository, times(1)).findById(any());
         verify(ingredientRepository, times(0)).save(any());

     }
     @Test
     void givenANonExistingIngredientID_whenCallsUpdateIngredient_shouldReturnException() {
         final var nonExistingId = 10;

         final String expectName = "Tomato";
         final String expectDescription = "Tomato";
         final IngredientCategory expectCategory = IngredientCategory.VEGETABLES;
         final Set<Integer> units =  new HashSet<>(Set.of(1, 2));

         final String expectErrorMsg = "Ingredient with ID: 10 was not found.";

         final IngredientRequest input = new IngredientRequest(expectName, expectDescription, expectCategory, units);

         when(ingredientRepository.findById(nonExistingId))
                 .thenReturn(Optional.empty());

         final var exception = Assertions.assertThrows(NotFoundException.class, () -> {
             ingredientService.update(nonExistingId, input);
         });

         Assertions.assertNotNull(exception);
         Assertions.assertEquals(expectErrorMsg, exception.getMessage());

         verify(unitRepo, times(0)).findAllByIdIn(units);
         verify(ingredientRepository, times(1)).findById(nonExistingId);
         verify(ingredientRepository, times(0)).existsByName(any());
         verify(ingredientRepository, times(0)).save(any());


     }
}
