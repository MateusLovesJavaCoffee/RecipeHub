package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.ServiceUnitTests;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.list.RecipeIngredientMatchResponse;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import br.com.mateusulrich.recipeservice.recipe.repositories.projections.ListRecipesProjection;
import br.com.mateusulrich.recipeservice.storage.service.StorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FindAllByIngredientsMatchUnitTest extends ServiceUnitTests {
    @InjectMocks
    private RecipeServiceImpl recipeService;

    @Mock
    private StorageService storageService;

    @Mock
    private RecipeRepository recipeRepository;

    @Override
    protected List<Object> getMocks() {
        return List.of(storageService, recipeRepository);
    }

    @Test
    void findAllByIngredientsMatchs_ShouldReturnPageOfRecipeIngredientMatchResponse() {
        final var expectedId = 2;
        final var expectedTitle = "Recipe Title";
        final var expectedImageUrl = "https://example.com/images/potato.jpg";
        final var expectedLikes = 50;
        final var expectedReadyInMinutes = 30;
        final var expectedDifficulty = "INTERMEDIATE";
        final var expectedMatchCount = 3L;

        Set<Integer> ingredients = Set.of(1, 2, 3,4,5,6,7);
        Pageable pageable = Pageable.unpaged();

        Page<ListRecipesProjection> page = new PageImpl<>(List.of(
                createMockProjection(
                        expectedId,
                        expectedTitle,
                        expectedImageUrl,
                        expectedLikes,
                        expectedReadyInMinutes,
                        expectedDifficulty,
                        expectedMatchCount)));

        when(recipeRepository
                .listRecipesMatchIngredients(eq(ingredients), eq(pageable))).thenReturn(page);

        var result = recipeService.findAllByIngredientsMatchs(ingredients, pageable);

        verify(recipeRepository).listRecipesMatchIngredients(eq(ingredients), eq(pageable));

        assertNotNull(result);
        assertEquals(1, result.items().size());

        RecipeIngredientMatchResponse response1 = result.items().get(0);
        assertEquals(expectedId, response1.getId());
        assertEquals(expectedTitle, response1.getTitle());
        assertEquals(expectedImageUrl, response1.getImageUrl());
        assertEquals(expectedLikes, response1.getLikes());
        assertEquals(expectedReadyInMinutes, response1.getReadyInMinutes());
        assertEquals(expectedDifficulty, response1.getDifficulty());
        assertEquals(expectedMatchCount, response1.getUsedIngredientCount());
        assertEquals(43, response1.getCompatiblePercentage());
        assertEquals(4, response1.getMissedIngredientCount());
    }

    private ListRecipesProjection createMockProjection(
            Integer id,
            String title,
            String imageUrl,
            int likes,
            int readyInMinutes,
            String difficulty,
            Long ingredientMatchCount

    ) {
        return new ListRecipesProjection() {
            @Override
            public Integer getId() {
                return id;
            }

            @Override
            public String getTitle() {
                return title;
            }

            @Override
            public String getImageUrl() {
                return imageUrl;
            }

            @Override
            public int getLikes() {
                return likes;
            }

            @Override
            public int getReadyInMinutes() {
                return readyInMinutes;
            }

            @Override
            public String getDifficulty() {
                return difficulty;
            }
            @Override
            public Long getIngredientMatchCount() {
                return ingredientMatchCount;
            }
        };
    }

}
