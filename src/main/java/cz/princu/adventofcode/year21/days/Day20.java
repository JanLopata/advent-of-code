package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coords;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Day20 extends Day {
    public static void main(String[] args) throws IOException {
        new Day20().printParts();
    }

    private static final int BORDER_SIZE = 6;

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");
        String enhancement = input[0];

        var imageLines = input[1].split("\n");

        boolean[][] map = initEmpty(imageLines.length + 2 * BORDER_SIZE, imageLines[0].length() + 2 * BORDER_SIZE);

        for (int i = 0; i < imageLines.length; i++) {
            var chars = imageLines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
//                map[i + BORDER_SIZE][j + BORDER_SIZE] = chars[j] == '#';
            }
        }
        printMap(map);

        int stepsCount = 2;
        for (int i = 0; i < stepsCount; i++) {
            var enhanced = doEnhancement(enhancement, map);
            printMap(enhanced);
            map = enhanced;
        }

        return countLit(map, stepsCount + 1);
    }

    private boolean[][] initEmpty(int x, int y) {
        boolean[][] map = new boolean[x][];
        for (int i = 0; i < x; i++) {
            map[i] = new boolean[y];
        }
        return map;
    }

    private long countLit(boolean[][] map, int stepsCount) {
        long result = 0L;

        for (int i = stepsCount; i < map.length - stepsCount; i++) {
            for (int j = stepsCount; j < map[i].length - stepsCount; j++) {
                if (map[i][j])
                    result++;
            }
        }

        return result;
    }

    private boolean[][] doEnhancement(String enhancement, boolean[][] map) {
        boolean[][] enhancedMap = initEmpty(map.length, map[0].length);

        for (int i = 1; i < enhancedMap.length - 1; i++) {
            for (int j = 1; j < enhancedMap[i].length - 1; j++) {
                int surrounding = surroundingToNumber(map, i, j);
                enhancedMap[i][j] = enhancement.charAt(surrounding) == '#';
            }
        }

        return enhancedMap;
    }

    private int surroundingToNumber(boolean[][] map, int x, int y) {
        int result = 0;
        int magnitude = 256;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                result += (map[x + i][y + j] ? 1 : 0) * magnitude;
                magnitude /= 2;
            }
        }
        return result;
    }

    private void printMap(boolean[][] map) {

        StringBuilder sb = new StringBuilder();

        for (boolean[] booleans : map) {
            for (boolean aBoolean : booleans) {
                sb.append(aBoolean ? "#" : ".");
            }
            sb.append("\n");
        }

        log.info("Map:\n{}", sb);
    }

    private Set<Coords> doEnhancement(String enhancement, Set<Coords> map, boolean universeLit) {
        var minX = map.stream().mapToInt(Coords::getI).min().orElse(-1);
        var maxX = map.stream().mapToInt(Coords::getI).max().orElse(-1);
        var minY = map.stream().mapToInt(Coords::getJ).min().orElse(-1);
        var maxY = map.stream().mapToInt(Coords::getJ).max().orElse(-1);

        if (universeLit) {
            for (int i = minX - 1; i <= maxX + 1; i++) {
                map.add(new Coords(i, minY - 1));
                map.add(new Coords(i, maxY + 1));
            }
            for (int i = minY - 1; i <= maxY + 1; i++) {
                map.add(new Coords(minX - 1, i));
                map.add(new Coords(maxX + 1, i));
            }
        }

        Set<Coords> result = new HashSet<>();

        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {

                int surrounding = surroundingToNumber(map, new Coords(x, y));

                if (enhancement.charAt(surrounding) == '#') {
                    result.add(new Coords(x, y));
                }

            }
        }


        return result;
    }

    private int surroundingToNumber(Set<Coords> map, Coords coords) {

        int result = 0;
        int magnitude = 256;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                result += (map.contains(coords.plus(i, j)) ? 1 : 0) * magnitude;
                magnitude /= 2;
            }
        }
        return result;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 20;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
