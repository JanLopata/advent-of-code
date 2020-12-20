package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Day20 extends Day {
    public static void main(String[] args) throws IOException {
        new Day20().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        List<Tile> tiles = new ArrayList<>();
        for (String s : input) {
            tiles.add(initTile(s));
        }

        Map<String, Set<Long>> knownSides = new HashMap<>();

        for (Tile tile : tiles) {

            final char[][] topSides = tile.getTopSides();
            for (char[] topSide : topSides) {
                final String sideAsString = String.valueOf(topSide);
                knownSides.putIfAbsent(sideAsString, new HashSet<>());
                knownSides.get(sideAsString).add(tile.getId());
            }

        }

        return 0L;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    public Tile initTile(String input) {

        final String[] lines = input.split("\n");
        long id = Long.parseLong(lines[0].split(" ")[1].replace(":", ""));

        char[][] pixels = new char[lines[1].length()][];

        for (int i = 1; i < lines.length; i++) {
            pixels[i - 1] = lines[i].toCharArray();
        }

        return new Tile(pixels, id);
    }


    @ToString
    @Getter
    @Setter
    private static class Tile {

        private char[][] grid;
        private char[][] v1;
        private char[][] v2;
        private char[][] v3;

        private long id;
        private int usedVariant;

        public char[][] getTopSides() {

            final char[][] sides = new char[4][];
            sides[0] = grid[0];
            sides[1] = grid[1];
            sides[2] = grid[2];
            sides[3] = grid[3];

            return sides;
        }

        public Tile(char[][] grid, long id) {

            this.grid = grid;

            if (grid.length != grid[0].length)
                throw new IllegalArgumentException("uneven sides");

            v1 = rotate(1, grid);
            v2 = rotate(2, grid);
            v3 = rotate(3, grid);

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


    @Override
    public int getDayNumber() {
        return 20;
    }


    @Override
    public int getYear() {
        return 2020;
    }
}
