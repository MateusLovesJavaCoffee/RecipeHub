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
    private Integer id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "image_url", length = 250)
    private String imageUrl;

    @Column(length = 200)
    private String description;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private IngredientCategory category;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "possible_units",
            joinColumns = @JoinColumn(name = "ingredient_id"),
            inverseJoinColumns = @JoinColumn(name = "unit_of_measure_id")
    )
    private Set<UnitOfMeasure> possibleUnits;

    public Set<UnitOfMeasure> getPossibleUnits() {
        return possibleUnits != null ? possibleUnits : new HashSet<>();
    }
    public void addPossibleUnit(final UnitOfMeasure unit) {
        if (possibleUnits != null) {
            this.possibleUnits.add(unit);
        }

    }

    public Ingredient(String name, String description, IngredientCategory category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Ingredient(String name, String description, IngredientCategory category, Set<UnitOfMeasure> possibleUnits) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.possibleUnits = possibleUnits;
    }

    public Ingredient(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.imageUrl = ingredient.getImageUrl();
        this.description = ingredient.getDescription();
        this.category = ingredient.getCategory();
        this.possibleUnits = ingredient.getPossibleUnits();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}