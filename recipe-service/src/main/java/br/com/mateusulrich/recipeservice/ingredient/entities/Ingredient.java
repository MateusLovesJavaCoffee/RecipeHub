package br.com.mateusulrich.recipeservice.ingredient.entities;

import br.com.mateusulrich.recipeservice.ingredient.enums.IngredientCategory;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "short_description", length = 250)
    private String shortDescription;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "possible_units",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_unit_id")
    )
    private Set<IngredientUnit> possibleUnits;

    public Set<IngredientUnit> getPossibleUnits() {
        return possibleUnits != null ? possibleUnits : new HashSet<>();
    }
    public void addPossibleUnit(final IngredientUnit unit) {
        if (possibleUnits != null) {
            this.possibleUnits.add(unit);
        }

    }

    public Ingredient(String name, String shortDescription, IngredientCategory category) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientUnit that = (IngredientUnit) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}