package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceIntegrationTests;
import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.UnitOfMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ServiceIntegrationTests
public class IngredientServiceIntegrationTest {

    @Autowired
    private IngredientServiceImpl service;

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;
    @Autowired
    private IngredientRepository ingredientRepository;


    @Test
    void givenAValidIngredient_whenCallsCreateIngredientWithUnitOfMeasure_thenSuccess() {
        UnitOfMeasure expectedUnit = unitOfMeasureRepository.save(new UnitOfMeasure( "UnitOfMeasure1"));
        unitOfMeasureRepository.flush();
        final Integer expectedUnitId = expectedUnit.getId();
        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;
        final Ingredient ingredient = new Ingredient(expectedName, expectedDescription, expectedCategory);

        assertNull(ingredient.getId());

        final Ingredient ingredientCreated = service.save(Set.of(expectedUnitId), ingredient);

        final Integer ingredientId = ingredientCreated.getId();

        assertEquals(expectedName, ingredientCreated.getName());
        assertEquals(expectedDescription, ingredientCreated.getDescription());
        assertNull(ingredientCreated.getImageUrl());
        assertEquals(expectedCategory, ingredientCreated.getCategory());

        final Ingredient ingredientSaved = ingredientRepository.findById(ingredientId).orElse(null);

        assertNotNull(ingredientSaved);
        assertEquals(expectedName, ingredientSaved.getName());
        assertEquals(expectedDescription, ingredientSaved.getDescription());
        assertNull(ingredientSaved.getImageUrl());
        assertEquals(expectedCategory, ingredientSaved.getCategory());
        assertTrue(ingredientSaved.getPossibleUnits().stream().anyMatch(x -> Objects.equals(x.getId(), expectedUnitId)));

    }

    @Test
    void givenAValidIngredientWithoutUnits_whenCallsCreateIngredient_thenSuccess() {
        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;

        final Ingredient ingredient = new Ingredient(expectedName, expectedDescription, expectedCategory);
        assertNull(ingredient.getId());

        final Ingredient ingredientCreated = service.save(Set.of(), ingredient);

        final Integer ingredientId = ingredientCreated.getId();

        assertEquals(expectedName, ingredientCreated.getName());
        assertEquals(expectedDescription, ingredientCreated.getDescription());
        assertNull(ingredientCreated.getImageUrl());
        assertEquals(expectedCategory, ingredientCreated.getCategory());

        final Ingredient ingredientSaved = ingredientRepository.findById(ingredientId).orElse(null);

        assertNotNull(ingredientSaved);
        assertEquals(expectedName, ingredientSaved.getName());
        assertEquals(expectedDescription, ingredientSaved.getDescription());
        assertNull(ingredientSaved.getImageUrl());
        assertEquals(expectedCategory, ingredientSaved.getCategory());
        assertTrue(ingredientSaved.getPossibleUnits().isEmpty());
    }


    @Test
    public void givenAValidExistingId_whenCallsDeleteIngredient_thenSuccess() {
        UnitOfMeasure expectedUnit = unitOfMeasureRepository.save(new UnitOfMeasure( "UnitOfMeasure1"));
        unitOfMeasureRepository.flush();
        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;
        final Ingredient ingredient = new Ingredient(expectedName, expectedDescription, expectedCategory);
        ingredient.setPossibleUnits(Set.of(expectedUnit));
        final var existingId = ingredientRepository.saveAndFlush(ingredient).getId();

        Assertions.assertEquals(1, ingredientRepository.count());

        Assertions.assertTrue(ingredientRepository.existsById(existingId));
        service.delete(existingId);
        Assertions.assertFalse(ingredientRepository.existsById(existingId));
    }

    @Test
    public void givenANonExistingId_whenCallsDeleteIngredient_thenThrowsException() {
        final var nonExistingId = 1000;

        String expectedErrorMsg = "Ingredient with ID: 1000 was not found.";

        Assertions.assertFalse(ingredientRepository.existsById(nonExistingId));
        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            service.delete(nonExistingId);
        });
        Assertions.assertEquals(expectedErrorMsg, ex.getMessage());
    }

    @Test
    void givenAValidIngredientID_whenCallsUpdateIngredientWithUnitOfMeasure_thenSuccess() {
        UnitOfMeasure expectedUnit = unitOfMeasureRepository.saveAndFlush(new UnitOfMeasure( "UnitOfMeasure1"));
        final Integer expectedUnitId = expectedUnit.getId();

        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Ingredient Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;

        final Ingredient toUpdate = new Ingredient("Ingredient Name", "Ingredient Description", IngredientCategory.VEGETABLES);

        assertNull(toUpdate.getId());
        Assertions.assertEquals(0, ingredientRepository.count());

        final Ingredient toUpdatePersisted = service.save(Set.of(), toUpdate);

        Assertions.assertEquals("Ingredient Name", toUpdate.getName());
        Assertions.assertEquals(0, toUpdate.getPossibleUnits().size());

        final Integer ingredientId = toUpdatePersisted.getId();

        final var ingredientUpdated = service.update(
                ingredientId, new IngredientRequest(expectedName, expectedDescription, expectedCategory, Set.of(expectedUnitId)));

        Assertions.assertEquals(1, ingredientRepository.count());

        assertEquals(expectedName, ingredientUpdated.getName());
        assertEquals(expectedDescription, ingredientUpdated.getDescription());
        assertNull(ingredientUpdated.getImageUrl());
        assertEquals(expectedCategory, ingredientUpdated.getCategory());

        final Ingredient ingredientSaved = ingredientRepository.findById(ingredientId).orElse(null);

        assertNotNull(ingredientSaved);
        assertEquals(expectedName, ingredientSaved.getName());
        assertEquals(expectedDescription, ingredientSaved.getDescription());
        assertNull(ingredientSaved.getImageUrl());
        assertEquals(expectedCategory, ingredientSaved.getCategory());
        assertTrue(ingredientSaved.getPossibleUnits().stream().anyMatch(x -> Objects.equals(x.getId(), expectedUnitId)));

    }

    @Test
    void givenAValidIngredientID_whenCallsUpdateIngredientCleaningUnits_thenSuccess() {
        UnitOfMeasure expectedUnit = unitOfMeasureRepository.saveAndFlush(new UnitOfMeasure( "UnitOfMeasure1"));
        final Integer expectedUnitId = expectedUnit.getId();

        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Ingredient Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;

        final Ingredient toUpdate = new Ingredient("Ingredient Name", "Ingredient Description", IngredientCategory.VEGETABLES);

        assertNull(toUpdate.getId());
        Assertions.assertEquals(0, ingredientRepository.count());

        final Ingredient toUpdatePersisted = service.save(Set.of(expectedUnitId), toUpdate);

        Assertions.assertEquals("Ingredient Name", toUpdate.getName());
        Assertions.assertEquals(1, toUpdate.getPossibleUnits().size());

        final Integer ingredientId = toUpdatePersisted.getId();

        final var ingredientUpdated = service.update(
                ingredientId,  new IngredientRequest(expectedName, expectedDescription, expectedCategory, Set.of()));

        Assertions.assertEquals(1, ingredientRepository.count());

        assertEquals(expectedName, ingredientUpdated.getName());
        assertEquals(expectedDescription, ingredientUpdated.getDescription());
        assertNull(ingredientUpdated.getImageUrl());
        assertEquals(expectedCategory, ingredientUpdated.getCategory());
        assertTrue(ingredientUpdated.getPossibleUnits().isEmpty());

        final Ingredient ingredientSaved = ingredientRepository.findById(ingredientId).orElse(null);

        assertNotNull(ingredientSaved);
        assertEquals(expectedName, ingredientSaved.getName());
        assertEquals(expectedDescription, ingredientSaved.getDescription());
        assertNull(ingredientSaved.getImageUrl());
        assertEquals(expectedCategory, ingredientSaved.getCategory());
        assertTrue(ingredientUpdated.getPossibleUnits().isEmpty());
    }

    @Test
    void givenAValidIngredientID_whenFindingIngredientById_thenSuccess() {
        UnitOfMeasure expectedUnit = unitOfMeasureRepository.save(new UnitOfMeasure( "UnitOfMeasure1"));
        unitOfMeasureRepository.flush();
        final String expectedName = "Ingredient Name Test";
        final String expectedDescription = "Description Test";
        final IngredientCategory expectedCategory = IngredientCategory.CONDIMENTS;
        final Ingredient ingredient = new Ingredient(expectedName, expectedDescription, expectedCategory);
        ingredient.setPossibleUnits(Set.of(expectedUnit));
        final var ingredientId = ingredientRepository.saveAndFlush(ingredient).getId();

        Assertions.assertEquals(1, ingredientRepository.count());


        final var actualIngredient = service.findOrThrowNotFound(ingredientId);

        Assertions.assertEquals(ingredientId, actualIngredient.getId());
        Assertions.assertEquals(expectedName, actualIngredient.getName());
        Assertions.assertEquals(expectedDescription, actualIngredient.getDescription());
        Assertions.assertEquals(expectedCategory, actualIngredient.getCategory());
        Assertions.assertEquals(1, actualIngredient.getPossibleUnits().size());
        Assertions.assertTrue(actualIngredient.getPossibleUnits().contains(expectedUnit));
    }

    @Test
    void givenANonExistingID_whenCallsFindIngredientByID_thenReturnNotFound() {
        final var nonExistingID = 1000;
        final var expectedErrorMessage = "Ingredient with ID: 1000 was not found.";
        Assertions.assertEquals(0, ingredientRepository.count());

        NotFoundException ex = Assertions.assertThrows(NotFoundException.class, () -> {
            service.findOrThrowNotFound(nonExistingID);
        });

        Assertions.assertEquals(expectedErrorMessage, ex.getMessage());

    }
}
