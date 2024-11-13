package br.com.mateusulrich.recipeservice.recipe.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
public class InstructionID implements Serializable {

    @Column(name = "instruction_number_id", nullable = false)
    private Integer instructionNumberId;
    @Column(name = "recipe_id", nullable = false)
    private Integer recipeId;

    public InstructionID(Integer instructionNumberId, Integer recipeId) {
        this.instructionNumberId = instructionNumberId;
        this.recipeId = recipeId;
    }
    public InstructionID() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstructionID that = (InstructionID) o;
        return Objects.equals(getInstructionNumberId(), that.getInstructionNumberId()) && Objects.equals(getRecipeId(), that.getRecipeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getInstructionNumberId(), getRecipeId());
    }
}
