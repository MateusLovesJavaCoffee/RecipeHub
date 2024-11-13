package br.com.mateusulrich.recipeservice.common.specifications;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import br.com.mateusulrich.recipeservice.recipe.entity.IngredientComposition;
import br.com.mateusulrich.recipeservice.recipe.entity.Recipe;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.Set;

public class SpecificationTemplate {

    public static Specification<Ingredient> hasCategoryAndName(final String category, final String name) {
        return (root, query, cb) -> {
            Predicate predicate = cb.conjunction();
            if (category != null && !category.isEmpty()) {
                predicate = cb.and(predicate, cb.equal(root.get("category"), category));
            }
            if (name != null && !name.isEmpty()) {
                predicate = cb.and(predicate, cb.like(root.get("name"), "%" + name + "%"));
            }
            return predicate;
        };
    }
    public static Specification<Recipe> hasIngredients(final Set<Integer> ingredientIds) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<Recipe, IngredientComposition> ingredientCompositionJoin = root.join("ingredientCompositions", JoinType.INNER);
            Join<IngredientComposition, Ingredient> ingredientJoin = ingredientCompositionJoin.join("ingredient", JoinType.INNER);
            query.groupBy(root.get("id"));
            query.orderBy(cb.desc(cb.count(ingredientJoin.get("id"))));
            return ingredientJoin.get("id").in(ingredientIds);
        };
    }








}
