package cz.princu.adventofcode.year20.days.day20;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ToString(of = "id")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Tile {

    private char[][] grid;
    private char[][] v1;
    private char[][] v2;
    private char[][] v3;

    private char[][] gridF;
    private char[][] v1F;
    private char[][] v2F;
    private char[][] v3F;

    private long id;

    public static Tile initTile(String input) {

        final String[] lines = input.split("\n");
        long id = Long.parseLong(lines[0].split(" ")[1].replace(":", ""));

        char[][] pixels = new char[lines[1].length()][];

        for (int i = 1; i < lines.length; i++) {
            pixels[i - 1] = lines[i].toCharArray();
        }

        return new Tile(pixels, id);
    }

    public List<String> getTopSides() {

        List<String> result = new ArrayList<>(4);
        result.add(String.valueOf(grid[0]));
        result.add(String.valueOf(v1[0]));
        result.add(String.valueOf(v2[0]));
        result.add(String.valueOf(v3[0]));

        return result;
    }

    public Tile(char[][] grid, long id) {

        this.grid = grid;
        this.id = id;

        if (grid.length != grid[0].length)
            throw new IllegalArgumentException("uneven sides");

        v1 = rotate(1, grid);
        v2 = rotate(2, grid);
        v3 = rotate(3, grid);

        gridF = flip(grid);
        v1F = rotate(1, gridF);
        v2F = rotate(2, gridF);
        v3F = rotate(3, gridF);

    }

    private static char[][] flip(char[][] original) {
        final int size = original.length;
        char[][] result = new char[size][];

        for (int i = 0; i < size; i++) {
            char[] resultRow = new char[size];

            for (int j = 0; j < size; j++) {

                resultRow[size - j - 1] = original[i][j];
            }
            result[i] = resultRow;
        }
        return result;
    }

    private static char[][] rotate(int variant, char[][] original) {

        final int size = original.length;
        char[][] result = new char[size][];
        for (int i = 0; i < size; i++) {
            char[] resultRow = new char[size];
            for (int j = 0; j < size; j++) {
                resultRow[j] = original[getICoord(variant, size, i, j)][getJCoord(variant, size, i, j)];
            }
            result[i] = resultRow;
        }
        return result;
    }


    private static int getICoord(int variant, int size, int i, int j) {
        if (variant == 1)
            return size - j - 1;

        if (variant == 2)
            return size - i - 1;

        if (variant == 3)
            return j;

        throw new IllegalArgumentException("unknown variant");
    }

    private static int getJCoord(int variant, int size, int i, int j) {
        if (variant == 1)
            return i;

        if (variant == 2)
            return size - j - 1;

        if (variant == 3)
            return size - i - 1;

        throw new IllegalArgumentException("unknown variant");
    }


}
