package br.com.mateusulrich.recipeservice.ingredient.service;

import br.com.mateusulrich.recipeservice.common.exception.EntityInUseException;
import br.com.mateusulrich.recipeservice.common.exception.IngredientNameAlreadyExistsException;
import br.com.mateusulrich.recipeservice.common.exception.MissingIdentifiersException;
import br.com.mateusulrich.recipeservice.common.exception.NotFoundException;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientInputData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.UnitOfMeasure;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.UnitOfMeasureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final UnitOfMeasureRepository unitRepo;
    private final IngredientRepository ingredientRepository;

    @Override
    @Transactional(readOnly = true)
    public IngredientResponse findIngredientById(Integer id) {
        final Ingredient ingredient = getOrThrowNotFound(id);
        return IngredientResponse.from(ingredient);
    }

    @Override
    @Transactional
    public IngredientResponse createIngredient(IngredientInputData data) {
        assertNameDoesNotExist(data.name());
        Set<Integer> unitIds = data.possibleUnits();
        Set<UnitOfMeasure> unitOfMeasures = unitRepo.findAllByIdIn(unitIds);
        assertExistsIds(unitOfMeasures, unitIds);
        Ingredient ingredient = new Ingredient(data.name(), data.description(), data.category(), unitOfMeasures);
        ingredientRepository.save(ingredient);
        return IngredientResponse.from(ingredient);
    }

    @Override
    @Transactional
    public void updateIngredient(Integer id, IngredientInputData data) {
        Ingredient ingredient = ingredientRepository.findIngredientWithUnits(id)
                .orElseThrow(() -> NotFoundException.with(Ingredient.class, id));

        Set<UnitOfMeasure> unitOfMeasures = unitRepo.findAllByIdIn(data.possibleUnits());

        if (!Objects.equals(data.name(), ingredient.getName())) {
            assertNameDoesNotExist(data.name());
        }
        assertExistsIds(unitOfMeasures, data.possibleUnits());

        ingredient.setName(data.name());
        ingredient.setDescription(data.description());
        ingredient.setCategory(data.category());
        ingredient.getPossibleUnits().clear();
        for (UnitOfMeasure unit : unitOfMeasures) {
            ingredient.addPossibleUnit(unit);
        }
        ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void deleteIngredient(final Integer id) {
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
            Specification<Ingredient> spec, Pageable pageable) {
        return ingredientRepository.findAll(spec, pageable).map(IngredientResponse::from);
    }
    public Ingredient getOrThrowNotFound(Integer id) {
        return ingredientRepository.findById(id).orElseThrow(() -> NotFoundException.with(Ingredient.class, id));
    }
    private void assertNameDoesNotExist(String name) {
        if (ingredientRepository.existsByName(name) ){
            throw new IngredientNameAlreadyExistsException("Ingredient name must be unique.");
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
