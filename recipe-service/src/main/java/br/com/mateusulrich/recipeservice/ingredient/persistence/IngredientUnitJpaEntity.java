package br.com.mateusulrich.recipeservice.ingredient.persistence;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity(name = "IngredientUnit")
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ingredient_units")
public class IngredientUnitJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "unit_name", nullable = false, unique = true)
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientUnitJpaEntity that = (IngredientUnitJpaEntity) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}