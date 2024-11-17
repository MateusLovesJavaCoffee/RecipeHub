package br.com.mateusulrich.recipeservice.recipe.service;

import br.com.mateusulrich.recipeservice.api.dtos.recipe.retrieve.list.RecipeIngredientMatchResponse;
import br.com.mateusulrich.recipeservice.common.exception.AmazonS3Exception;
import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.common.pagination.PageResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import br.com.mateusulrich.recipeservice.recipe.repositories.RecipeRepository;
import br.com.mateusulrich.recipeservice.recipe.repositories.projections.ListRecipesProjection;
import br.com.mateusulrich.recipeservice.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;


@Service
@RequiredArgsConstructor
@Log4j2
public class RecipeServiceImpl implements RecipeService {

    public static final String RECIPE_FOLDER = "Ingredients/%s/photo";
    private final RecipeRepository recipeRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void updatePhoto(Recipe recipe, MultipartFile file) throws IOException {
        String folder = RECIPE_FOLDER.formatted(recipe.getId());
        storageService.store(file, folder).thenAccept(uploadedUrl -> {
            recipe.setImgUrl(uploadedUrl);
            recipeRepository.save(recipe);
        }).exceptionally(ex -> {
            log.error("Error uploading photo: {}", ex.getMessage());
            throw new AmazonS3Exception("Error uploading photo", ex.getCause());
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Recipe findOrThrowNotFound(Integer id) {
        return findOrElseThrow(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Recipe save(Recipe data) {
        return recipeRepository.save(data);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<RecipeIngredientMatchResponse> findAllByIngredientsMatchs(Set<Integer> ingredients, Pageable pageable) {
        Page<ListRecipesProjection> projections = recipeRepository.listRecipesMatchIngredients(ingredients, pageable);

        return new PageResponse<>(
                projections.getNumber(),
                projections.getSize(),
                projections.getTotalElements(),
                projections.map(x -> new RecipeIngredientMatchResponse(ingredients, x)).toList()
        );
    }

    @Transactional
    public void delete(Integer id) {
        if (!recipeRepository.existsById(id)) {
            throw NotFoundException.with(Ingredient.class, id);
        }
        try {
            recipeRepository.deleteById(id);
            storageService.delete(RECIPE_FOLDER.formatted(id));
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

    protected Recipe findOrElseThrow(Integer id) {
       return recipeRepository.findById(id).orElseThrow(() -> NotFoundException.with(Recipe.class, id));
    }


}
