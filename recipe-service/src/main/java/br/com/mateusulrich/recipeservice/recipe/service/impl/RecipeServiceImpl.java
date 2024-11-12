package br.com.mateusulrich.recipeservice.recipe.service.impl;

import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.recipe.dto.input.RecipeInputData;
import br.com.mateusulrich.recipeservice.recipe.dto.RecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.dto.retrieve.GetRecipeResponse;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.mapper.RecipeMapper;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import br.com.mateusulrich.recipeservice.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeMapper recipeMapper;

    @Override
    @Transactional(readOnly = true)
    public GetRecipeResponse getById(Long id) {
        final Recipe recipe = findOrElseThrow(id);
        return recipeMapper.fromEntityToGetResponse(recipe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse save(RecipeInputData recipeInputData) {
        Recipe recipe = recipeMapper.fromCreationDtoToEntity(recipeInputData);
        recipeRepository.save(recipe);
        return recipeMapper.fromEntityToResponseDto(recipe);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RecipeResponse update(Long id, RecipeInputData createRecipeData) {
        Recipe recipe = findOrElseThrow(id);
        recipeMapper.update(recipe, createRecipeData);
        return recipeMapper.fromEntityToResponseDto(recipeRepository.save(recipe));
    }
    @Transactional
    public void delete(Long id) {
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
    protected Recipe findOrElseThrow(Long id) {
       return recipeRepository.findById(id).orElseThrow(() -> NotFoundException.with(Recipe.class, id));
    }


}
