package cz.princu.adventofcode.common.utils;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ArrayIndexChecker {

    private final int maxI;
    private final int maxJ;

    public boolean isOutOfBounds(int i, int j) {
        if (i < 0)
            return true;

        if (j < 0)
            return true;

        if (i >= maxI)
            return true;

        return j >= maxJ;
    }

    public boolean isOutOfBounds(Coords coords) {
        return isOutOfBounds(coords.i, coords.j);
    }
}
