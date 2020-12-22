package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day20 extends Day {
    public static void main(String[] args) throws IOException {
        new Day20().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        HashMap<Long, Tile> tiles = new HashMap<>();
        for (String s : input) {

            final Tile tile = initTile(s);
            tiles.put(tile.getId(), tile);
        }

        Map<String, Set<Long>> knownSides = new HashMap<>();

        for (Tile tile : tiles.values()) {

            final List<String> topSides = tile.getTopSides();

            for (int i = 0; i < topSides.size(); i++) {
                String sideAsString = String.valueOf(topSides.get(i));
                final String reversedString = StringUtils.reverse(sideAsString);
                knownSides.putIfAbsent(reversedString, new HashSet<>());
                knownSides.get(reversedString).add(tile.getId() * 4 + i);
            }

        }

        Set<Tile> usedTiles = new HashSet<>();

        Set<Long> result = new HashSet<>();

        final Tile tile = tiles.values().stream().findAny().orElse(null);
        usedTiles.add(tile);

        solve(tiles, usedTiles, knownSides, result);

        return result.stream().reduce(Long::sum);

    }

    private void solve(HashMap<Long, Tile> tiles, Set<Tile> usedTiles, Map<String, Set<Long>> knownSides, Set<Long> result) {

        if (usedTiles.size() == tiles.size()) {
            log.info("solved but what now?");
            return;
        }

        final List<Tile> notCompletelyUsed = usedTiles.stream().filter(it -> it.getUsedSides().size() < 4).collect(Collectors.toList());

        for (Tile usedTile : notCompletelyUsed) {

            for (int variant = 0; variant < 4; variant++) {

                if (usedTile.getUsedSides().contains(variant))
                    continue;

                if (!knownSides.containsKey(usedTile.getTopSides().get(variant)))
                    continue;

                // find matching tile
                for (Long matchingTileId : knownSides.get(usedTile.getTopSides().get(variant))) {


                    Tile matchingTile = tiles.get(matchingTileId / 4);
                    int matchingVariant = (((int) (matchingTileId % 4)) + 2) % 4;

                    if (usedTiles.contains(matchingTile))
                        continue;

                    // use
                    usedTile.getUsedSides().add(variant);
                    matchingTile.getUsedSides().add(matchingVariant);
                    usedTiles.add(matchingTile);

                    solve(tiles, usedTiles, knownSides, result);

                    // unuse
                    usedTile.getUsedSides().remove(variant);
                    matchingTile.getUsedSides().remove(matchingVariant);
                    usedTiles.remove(matchingTile);

                }


                // use


                // unuse
            }

        }


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
    @EqualsAndHashCode(of = "id")
    private static class Tile {

        private char[][] grid;
        private char[][] v1;
        private char[][] v2;
        private char[][] v3;

        private long id;

        private Set<Integer> usedSides;

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

            usedSides = new HashSet<>(4);

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
