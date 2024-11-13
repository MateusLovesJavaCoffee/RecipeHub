package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInput;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.ListRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Override
    @Transactional(readOnly = true)
    public GetRecipeResponse getById(Integer id) {
        final Recipe recipe = findOrElseThrow(id);
        return GetRecipeResponse.fromEntityToGetResponse(recipe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse create(RecipeInput data) {
        return save(data, new Recipe());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse update(Integer id, RecipeInput data) {
        Recipe recipe = findOrElseThrow(id);
        return save(data, recipe);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<ListRecipeResponse> findAllByIngredients(Set<Integer> ingredients, Specification<Recipe> spec, Pageable pageable) {
        Page<Recipe> recipes = recipeRepository.findAll(spec, pageable);
        List<ListRecipeResponse> listRecipeResponses =
                recipes.getContent().stream() .map(recipe -> ListRecipeResponse.fromEntityToListResponse(ingredients, recipe)).collect(Collectors.toList());

        listRecipeResponses
                .sort(Comparator.comparingInt(ListRecipeResponse::getIngredientCount)
                        .reversed());
        return new PageResponse<>(
                recipes.getNumber(),
                recipes.getSize(),
                recipes.getTotalElements(),
                listRecipeResponses
        );
    }

    @Transactional
    public void delete(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw NotFoundException.with(Ingredient.class, id);
        }
        try {
            recipeRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("The entity cannot be modified or deleted as it is currently in use.");
        }

    }

    @Override
    @Transactional
    public void changeStatus(Integer id, Boolean status) {
        Recipe recipe = findOrElseThrow(id);
        recipe.changeStatus(status);
        recipeRepository.save(recipe);
    }

    private RecipeResponse save(RecipeInput data, Recipe recipe) {
        fromInputToEntity(data, recipe);
        recipeRepository.save(recipe);
        return RecipeResponse.fromEntityToResponseDto(recipe);
    }

    protected Recipe findOrElseThrow(Integer id) {
       return recipeRepository.findById(id).orElseThrow(() -> NotFoundException.with(Recipe.class, id));
    }

    private void fromInputToEntity(RecipeInput data, Recipe recipe) {
        recipe.setTitle(data.title());
        recipe.setPreparationMinutes(data.preparationMinutes());
        recipe.setDescription(data.description());
        recipe.setCookingMinutes(data.cookingMinutes());
        recipe.setServings(data.servings());
        recipe.setDifficulty(data.difficulty());
        recipe.setEstimatedCost(data.estimatedCost());
    }

}
