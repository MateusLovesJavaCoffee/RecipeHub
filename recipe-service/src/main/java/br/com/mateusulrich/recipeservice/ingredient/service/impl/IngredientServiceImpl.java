package br.com.mateusulrich.recipeservice.ingredient.service.impl;

import br.com.mateusulrich.recipeservice.common.exception.*;
import br.com.mateusulrich.recipeservice.ingredient.dtos.CreateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.dtos.IngredientResponse;
import br.com.mateusulrich.recipeservice.ingredient.dtos.UpdateIngredientData;
import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.ingredient.entities.IngredientUnit;
import br.com.mateusulrich.recipeservice.ingredient.mapper.IngredientMapper;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientRepository;
import br.com.mateusulrich.recipeservice.ingredient.repository.IngredientUnitRepository;
import br.com.mateusulrich.recipeservice.ingredient.service.IngredientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.hibernate.validator.constraints.Length;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
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
    public IngredientResponse createIngredient(final CreateIngredientData data) {
        Set<Long> unitIds = data.units();
        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(unitIds);
        assertExistsIds(ingredientUnits, unitIds);
        assertNameDoesNotExist(data.name());
        Ingredient ingredient = ingredientMapper.fromDtoToEntity(data);
        ingredientRepository.save(ingredient);

        return ingredientMapper.toIngredientResponse(ingredient);
    }

    @Override
    @Transactional
    public IngredientResponse updateIngredient(final UpdateIngredientData data) {
        Ingredient ingredient = ingredientRepository.findIngredientWithUnits(data.id())
                .orElseThrow(() -> NotFoundException.with(Ingredient.class, data.id()));

        Set<Long> unitIds = data.units();
        Set<IngredientUnit> ingredientUnits = unitRepo.findAllByIdIn(unitIds);

        assertExistsIds(ingredientUnits, unitIds);
        assertNameDoesNotExist(data.name());


        Set<IngredientUnit> toAdd = new HashSet<>(ingredientUnits);
        toAdd.removeAll(ingredient.getPossibleUnits());

        for (IngredientUnit unit : toAdd) {
            ingredient.addPossibleUnit(unit);
        }

        ingredient.setName(data.name());
        ingredient.setShortDescription(data.description());
        ingredient.setCategory(data.category());

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
        return ingredientRepository.findAll(pageable).map(IngredientResponse::from);
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
