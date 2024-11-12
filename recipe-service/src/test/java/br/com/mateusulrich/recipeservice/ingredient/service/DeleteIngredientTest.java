package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class DeleteIngredientTest extends ServiceUnitTests {

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
    void deleteShouldDoNothing_whenIngredientIdExists() {
    Long existingId = 1L;
    when(ingredientRepository.existsById(existingId)).thenReturn(true);
    doNothing().when(ingredientRepository).deleteById(existingId);
    Assertions.assertDoesNotThrow(() -> {
        ingredientService.deleteIngredient(existingId);
    });
    verify(ingredientRepository, Mockito.times(1)).existsById(existingId);
    verify(ingredientRepository, Mockito.times(1)).deleteById(existingId);

    }
    @Test
    void deleteShouldThrowNotFoundException_whenIdDoesNotExist() {
        Long nonExistingId = 2L;
        String expectedErrorMsg = "Ingredient with ID: 2 was not found.";

        when(ingredientRepository.existsById(nonExistingId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            ingredientService.deleteIngredient(nonExistingId);  // Chama o método que deve lançar a exceção
        });

        assertEquals(expectedErrorMsg, exception.getMessage());

        verify(ingredientRepository, times(0)).deleteById(nonExistingId);

        verify(ingredientRepository, times(1)).existsById(nonExistingId);
    }

    @Test
    void deleteShouldThrowEntityInUseException_whenDataIntegrityViolationOccurs() {
        Long id = 1L;
        when(ingredientRepository.existsById(id)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(ingredientRepository).deleteById(id);
        EntityInUseException exception = assertThrows(EntityInUseException.class, () -> {
            ingredientService.deleteIngredient(id);
        });
        assertEquals("The entity cannot be modified or deleted as it is currently in use.", exception.getMessage());
        verify(ingredientRepository, times(1)).deleteById(id);
        verify(ingredientRepository, times(1)).existsById(id);
    }
}
