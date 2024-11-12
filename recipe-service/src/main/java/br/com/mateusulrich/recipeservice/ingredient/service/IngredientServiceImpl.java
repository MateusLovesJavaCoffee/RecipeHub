package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.common.exception.*;
import br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static br.com.mateusulrich.recipeservice.common.specifications.SpecificationTemplate.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientUnitRepository unitRepo;
    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional(readOnly = true)
    public IngredientResponse findIngredientById(Long id) {
        final Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> NotFoundException.with(Ingredient.class, id));
        return IngredientResponse.from(ingredient);
    }

    @Override
    @Transactional
    public IngredientResponse createIngredient(IngredientInputData data) {
        assertNameDoesNotExist(data.name());
        Set<Long> unitIds = data.possibleUnits();
        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(unitIds);
        assertExistsIds(ingredientUnits, unitIds);
        Ingredient ingredient = new Ingredient(data.name(), data.shortDescription(), data.category(), ingredientUnits);
        ingredientRepository.save(ingredient);
        return IngredientResponse.from(ingredient);
    }

    @Override
    @Transactional
    public void updateIngredient(Long id, IngredientInputData data) {
        Ingredient ingredient = ingredientRepository.findIngredientWithUnits(id)
                .orElseThrow(() -> NotFoundException.with(Ingredient.class, id));

        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(data.possibleUnits());

        if (!Objects.equals(data.name(), ingredient.getName())) {
            assertNameDoesNotExist(data.name());
        }
        assertExistsIds(ingredientUnits, data.possibleUnits());

        ingredient.setName(data.name());
        ingredient.setShortDescription(data.shortDescription());
        ingredient.setCategory(data.category());
        ingredient.getPossibleUnits().clear();
        for (IngredientUnit unit : ingredientUnits) {
            ingredient.addPossibleUnit(unit);
        }
        ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteIngredient(final Long id) {
        if (!ingredientRepository.existsById(id)) {
            throw NotFoundException.with(Ingredient.class, id);
        }
        try {
            ingredientRepository.deleteById(id);
        }
        catch (DataIntegrityViolationException ex) {
            throw new EntityInUseException("The entity cannot be modified or deleted as it is currently in use.");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IngredientResponse> listAllIngredients(
            IngredientSpec spec, Pageable pageable) {
        return ingredientRepository.findAll(spec, pageable).map(IngredientResponse::from);
    }

    private void assertNameDoesNotExist(String name) {
        if (ingredientRepository.existsByName(name) ){
            throw new IngredientNameAlreadyExistsException("Ingredient name must be unique.");
        }
    }
    private void assertExistsIds(Set<IngredientUnit> entities, Set<Long> unitIds) {
        Set<Long> mutableUnitIds = new HashSet<>(unitIds);
        for (IngredientUnit entity : entities) {
            mutableUnitIds.remove(entity.getId());
        }
        if (!mutableUnitIds.isEmpty()) {
            String msg = mutableUnitIds.stream().map(Objects::toString).collect(Collectors.joining(", "));
            throw new MissingIdentifiersException("Some IngredientUnits could not be found: %s".formatted(msg));
        }
    }

}
