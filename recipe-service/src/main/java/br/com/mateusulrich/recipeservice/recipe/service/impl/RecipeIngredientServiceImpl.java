package br.com.mateusulrich.recipeservice.recipe.service.impl;

import br.com.mateusulrich.recipeservice.common.exception.DomainException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import br.com.mateusulrich.recipeservice.recipe.dto.input.IngredientCompositionInput;
import br.com.mateusulrich.recipeservice.recipe.jpa.Recipe;
import br.com.mateusulrich.recipeservice.recipe.jpa.RecipeIngredientComposition;
import br.com.mateusulrich.recipeservice.recipe.jpa.RecipeIngredientsID;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeIngredientRepository;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeIngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeIngredientServiceImpl implements RecipeIngredientService {

    private final RecipeServiceImpl recipeService;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final IngredientUnitRepository ingredientUnitRepository;
    private final RecipeIngredientRepository recipeIngredientRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Long recipeId, IngredientCompositionInput data) {
        save(recipeId, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long recipeId, IngredientCompositionInput data) {
        save(recipeId, data);
    }


    public void save(Long recipeId, IngredientCompositionInput data) {
        Recipe recipe = recipeService.findOrElseThrow(recipeId);
        Ingredient ingredient = ingredientRepository.findById(data.ingredientId())
                .orElseThrow(() -> NotFoundException.with(Ingredient.class, data.ingredientId()));
        IngredientUnit ingredientUnit = ingredientUnitRepository.findById(data.unitOfMeasureId())
                .orElseThrow(() -> NotFoundException.with(IngredientUnit.class, data.unitOfMeasureId()));

        Optional<RecipeIngredientComposition> existingComposition = recipeIngredientRepository
                .findByRecipeAndIngredient(recipeId, data.ingredientId());

        if (existingComposition.isPresent()) {
            RecipeIngredientComposition composition = existingComposition.get();

            composition.setAmount(data.amount());
            composition.setOrder(data.order());
            composition.setDescription(data.description());
            composition.setUnitOfMeasure(ingredientUnit);
            recipeIngredientRepository.save(composition);
        } else {
            recipe.addIngredientComposition(ingredient, data.amount(), data.order(), data.description(), ingredientUnit);
            recipeRepository.save(recipe);
        }
    }

    @Override
    @Transactional
    public void delete(Long recipeId, Long ingredientId) {
        final var id = new RecipeIngredientsID(ingredientId, recipeId);
        if (recipeIngredientRepository.existsById(id)) {
            try {
                recipeIngredientRepository.deleteById(id);
            }catch (Exception ex) {
                throw new DomainException(ex.getMessage(), null);
            }

        }
    }

//    private void assertRecipeDoesNotContainsThisIngredient(Recipe recipe, Ingredient ingredient) {
//        if (recipe.getIngredientCompositions().stream().anyMatch(x -> x.getIngredient().equals(ingredient))) {
//            throw new IngredientAlreadyExistsException("Ingredient already exists in the recipe");
//        }
//    }
}
