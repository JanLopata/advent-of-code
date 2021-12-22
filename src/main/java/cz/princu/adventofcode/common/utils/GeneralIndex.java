package cz.princu.adventofcode.common.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
public class GeneralIndex {

    private final int[] vars;

    // TODO: varargs
    public GeneralIndex(List<Integer> idx) {
        vars = new int[idx.size()];
        for (int i = 0; i < idx.size(); i++) {
            vars[i] = idx.get(i);
        }
    }

    public int getDimensionValue(int dim) {
        return vars[dim];
    }

    public GeneralIndex plus(GeneralIndex another) {
        if (another.getVars().length != this.vars.length)
            throw new IllegalArgumentException("incompatible dimensions");

        List<Integer> newIndices = new ArrayList<>();
        for (int i = 0; i < vars.length; i++) {
            newIndices.add(this.vars[i] + another.getVars()[i]);
        }
        return new GeneralIndex(newIndices);
    }

}
