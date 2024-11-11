package br.com.mateusulrich.recipeservice.ingredient.service.impl;

import br.com.mateusulrich.recipeservice.common.exception.*;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientDto;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.mapper.IngredientMapper;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
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

@Service
@Log4j2
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientUnitRepository unitRepo;
    private final IngredientRepository ingredientRepository;
    private final IngredientMapper ingredientMapper;

    @Override
    @Transactional(readOnly = true)
    public IngredientResponse findByIdOrThrowNotFound(Long id) {
        final Ingredient ingredient = ingredientRepository.findById(id).orElseThrow(() -> NotFoundException.with(Ingredient.class, id));
        return ingredientMapper.toIngredientResponse(ingredient);
    }

    @Override
    @Transactional
    public IngredientResponse createIngredient(IngredientDto data) {
        Set<Long> unitIds = data.possibleUnits();
        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(unitIds);
        assertExistsIds(ingredientUnits, unitIds);
        assertNameDoesNotExist(data.name());
        Ingredient ingredient = ingredientMapper.fromCreationDataToEntity(data);
        ingredient.setPossibleUnits(ingredientUnits);
        ingredientRepository.save(ingredient);

        return ingredientMapper.toIngredientResponse(ingredient);
    }

    @Override
    @Transactional
    public IngredientResponse updateIngredient(Long id, IngredientDto data) {
        Ingredient ingredient = ingredientRepository.findIngredientWithUnits(id)
                .orElseThrow(() -> NotFoundException.with(Ingredient.class, id));

        Set<Long> unitIds = data.possibleUnits();
        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(unitIds);

        assertExistsIds(ingredientUnits, unitIds);

        if (!Objects.equals(data.name(), ingredient.getName())) {
            assertNameDoesNotExist(data.name());
        }

        ingredientMapper.update(ingredient, data);

        ingredient.getPossibleUnits().clear();
        for (IngredientUnit unit : ingredientUnits) {
            ingredient.addPossibleUnit(unit);
        }
        ingredientRepository.save(ingredient);
        return ingredientMapper.toIngredientResponse(ingredient);
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
    public Page<IngredientResponse> listAllIngredients(Pageable pageable) {
        return ingredientRepository.findAll(pageable).map(ingredientMapper::toIngredientResponse);
    }

    private void assertNameDoesNotExist(String name) {
        if (ingredientRepository.existsByName(name) ){
            throw new IngredientNameAlreadyExistsException("Ingredient name must be unique.");
        }
    }
    private void assertExistsIds(Set<IngredientUnit> entities, Set<Long> unitIds) {
       for(IngredientUnit entity : entities) {
           unitIds.remove(entity.getId());
       }
        if (!unitIds.isEmpty()) {
            String msg = unitIds.stream().map(Objects::toString).collect(Collectors.joining(", "));
            throw new MissingIdentifiersException("Some IngredientUnits could not be found: %s".formatted(msg));
        }
    }

}
