package br.com.mateusulrich.recipeservice.recipe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "instructions")
public class Instruction {

    @EmbeddedId
    private InstructionID instructionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("recipeId")
    private Recipe recipe;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    public Instruction(Recipe recipe, Integer instructionNumber, String description) {
        this.instructionID = new InstructionID(instructionNumber, recipe.getId());
        this.recipe = recipe;
        this.description = description;
    }

    public Instruction(InstructionID instructionID, Recipe recipe, Integer instructionNumber, String description) {
        this.instructionID = instructionID;
        this.recipe = recipe;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return Objects.equals(getInstructionID(), that.getInstructionID());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getInstructionID());
    }
}