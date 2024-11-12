package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ListAllIngredientsTest extends ServiceUnitTests {
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

    private Pageable pageable;
    private IngredientSpec spec;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 10);
        spec = mock(IngredientSpec.class);
    }

    @Test
    void givenValidSpecAndPageable_whenListAllIngredients_thenReturnsPageOfIngredients() {
        Ingredient ingredient1 = new Ingredient("Tomato", "Tomato Description", IngredientCategory.VEGETABLES);
        ingredient1.setId(1L);
        Ingredient ingredient2 = new Ingredient("Carrot", "Carrot Description", IngredientCategory.VEGETABLES);
        ingredient2.setId(2L);

        Page<Ingredient> ingredientsPage = new PageImpl<>(List.of(ingredient1, ingredient2), pageable, 2);

        when(ingredientRepository.findAll(spec, pageable)).thenReturn(ingredientsPage);
        Page<IngredientResponse> result = ingredientService.listAllIngredients(spec, pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.getTotalElements());
        assertEquals(ingredient1.getName(), result.getContent().get(0).name());
        assertEquals(ingredient2.getName(), result.getContent().get(1).name());
        verify(ingredientRepository, times(1)).findAll(spec, pageable);
    }

    @Test
    void givenEmptyPage_whenListAllIngredients_thenReturnsEmptyPage() {
        Page<Ingredient> emptyPage = Page.empty(pageable);
        when(ingredientRepository.findAll(spec, pageable)).thenReturn(emptyPage);
        Page<IngredientResponse> result = ingredientService.listAllIngredients(spec, pageable);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

}
