package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.api.dtos.ingredient.IngredientRequest;
import br.com.mateusulrich.recipeservice.common.exception.*;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.UnitOfMeasureRepository;
import br.com.mateusulrich.recipeservice.storage.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    public static final String INGREDIENT_FOLDER = "Ingredients/%s/photo";
    private final UnitOfMeasureRepository unitRepo;
    private final IngredientRepository ingredientRepository;
    private final StorageService storageService;

    @Override
    @Transactional
    public void updatePhoto(Ingredient ingredient, MultipartFile file) throws IOException {
        String folder = INGREDIENT_FOLDER.formatted(ingredient.getId());
        storageService.store(file, folder).thenAccept(uploadedUrl -> {
            ingredient.setImageUrl(uploadedUrl);
            ingredientRepository.save(ingredient);
        }).exceptionally(ex -> {
            log.error("Error uploading photo: {}", ex.getMessage());
            throw new AmazonS3Exception("Error uploading photo", ex.getCause());
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Ingredient findOrThrowNotFound(Integer id) {
        return getOrThrowNotFound(id);
    }

    @Transactional
    @Override
    public Ingredient save(Set<Integer> unitIds, Ingredient toSave) {
        assertNameDoesNotExist(toSave.getName());
        Set<UnitOfMeasure> unitOfMeasures = findUnitOfMeasuresInIds(unitIds);
        assertExistsIds(unitOfMeasures, unitIds);
        toSave.setPossibleUnits(unitOfMeasures);
        return ingredientRepository.save(toSave);
    }

    @Override
    @Transactional
    public Ingredient update(Integer id, IngredientRequest inputData) {
        Ingredient toUpdate = findOrThrowNotFound(id);
        Set<UnitOfMeasure> unitOfMeasures = findUnitOfMeasuresInIds(inputData.possibleUnits());

        if (!Objects.equals(toUpdate.getName(), inputData.name())) {
            assertNameDoesNotExist(inputData.name());
        }
        assertExistsIds(unitOfMeasures, inputData.possibleUnits());
        toUpdate.update(inputData);
        toUpdate.getPossibleUnits().clear();
        for (UnitOfMeasure unit : unitOfMeasures) {
            toUpdate.addPossibleUnit(unit);
        }
        return ingredientRepository.save(toUpdate);
    }

    public Set<UnitOfMeasure> findUnitOfMeasuresInIds(Set<Integer> unitIds) {
        return unitRepo.findAllByIdIn(unitIds);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(final Integer id) throws DataIntegrityViolationException {
        if (!ingredientRepository.existsById(id)) {
            throw NotFoundException.with(Ingredient.class, id);
        }
        try {
            ingredientRepository.deleteById(id);
            storageService.delete(INGREDIENT_FOLDER.formatted(id));
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("The entity cannot be modified or deleted as it is currently in use.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Ingredient> listAllIngredients(
            Specification<Ingredient> spec, Pageable pageable) {
        return ingredientRepository.findAll(spec, pageable);
    }

    public Ingredient getOrThrowNotFound(Integer id) {
        return ingredientRepository.findById(id).orElseThrow(() -> NotFoundException.with(Ingredient.class, id));
    }
    private void assertNameDoesNotExist(String name) {
        if (ingredientRepository.existsByName(name)) {
            throw new IngredientNameAlreadyExistsException("The ingredient name '" + name + "' already exists.");
        }

    }
    private void assertExistsIds(Set<UnitOfMeasure> entities, Set<Integer> unitIds) {
        Set<Integer> mutableUnitIds = new HashSet<>(unitIds);
        for (UnitOfMeasure entity : entities) {
            mutableUnitIds.remove(entity.getId());
        }
        if (!mutableUnitIds.isEmpty()) {
            String msg = mutableUnitIds.stream().map(Objects::toString).collect(Collectors.joining(", "));
            throw new MissingIdentifiersException("Some IngredientUnits could not be found: %s".formatted(msg));
        }
    }

}
