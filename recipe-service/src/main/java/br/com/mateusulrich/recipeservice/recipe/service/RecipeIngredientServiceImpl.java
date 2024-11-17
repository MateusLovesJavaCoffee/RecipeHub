package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.common.exception.DomainException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.repository.UnitOfMeasureRepository;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientServiceImpl;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.RecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.api.dtos.recipe.input.UpdateRecipeIngredientRequest;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.entity.RecipeIngredient;
import br.com.mateusulrich.recipeservice.recipe.entity.RecipeIngredientsID;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeIngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeServiceImpl recipeService;
    private final IngredientServiceImpl ingredientService;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    @Transactional
    public void create(Integer recipeId, RecipeIngredientRequest data) {
        Recipe recipe = recipeService.findOrElseThrow(recipeId);
        Ingredient ingredient = ingredientService.getOrThrowNotFound(data.ingredientId());
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(data.unitOfMeasureId()).orElseThrow(() -> NotFoundException.with(UnitOfMeasure.class, data.unitOfMeasureId()));

        boolean ingredientExists = recipeIngredientRepository.existsIngredientIntoRecipe(recipeId, data.ingredientId());
        if (ingredientExists) {
            throw new DomainException("This ingredient is already associated with the recipe. Recipe ID: " + recipeId + ", Ingredient ID: " + ingredient.getId(), null);
        }

        RecipeIngredient composition = new RecipeIngredient(
                recipe,ingredient, data.amount(), data.order(), data.description(), unitOfMeasure
        );
        recipeIngredientRepository.save(composition);
    }

    @Override
    @Transactional
    public void update(Integer recipeId, Integer ingredientId, UpdateRecipeIngredientRequest data) {
        recipeService.findOrElseThrow(recipeId);
        ingredientService.getOrThrowNotFound(ingredientId);
        UnitOfMeasure unitOfMeasure = unitOfMeasureRepository.findById(data.unitOfMeasureId()).orElseThrow(() -> NotFoundException.with(UnitOfMeasure.class, data.unitOfMeasureId()));

        RecipeIngredient composition = recipeIngredientRepository.findByRecipeAndIngredient(recipeId, ingredientId);
        if (composition == null) {
            throw new DomainException("This ingredient is not associated with the recipe.", null);
        }

        composition.setAmount(data.amount());
        composition.setOrder(data.order());
        composition.setDescription(data.description());
        composition.setUnitOfMeasure(unitOfMeasure);

        recipeIngredientRepository.save(composition);
    }

    @Override
    @Transactional
    public void delete(Integer recipeId, Integer ingredientId) {
        final var id = new RecipeIngredientsID(ingredientId, recipeId);
        if (recipeIngredientRepository.existsById(id)) {
            try {
                recipeIngredientRepository.deleteById(id);
            }catch (Exception ex) {
                throw new DomainException(ex.getMessage(), ex.getCause());
            }

        }
    }
}
