package br.com.mateusulrich.recipeservice.common.specifications;

import br.com.mateusulrich.recipeservice.ingredient.entities.Ingredient;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

public class SpecificationTemplate {

    @And({
            @Spec(path = "category", spec = Equal.class),
            @Spec(path = "name", spec = Like.class)
    })
    public interface IngredientSpec extends Specification<Ingredient> {}


}
