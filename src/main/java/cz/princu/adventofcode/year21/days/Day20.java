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

    @Override
    public Object part1(String data) {

        var stepsCount = 2;
        return solveForGivenCountOfSteps(data, stepsCount);
    }

    @Override
    public Object part2(String data) {

        var stepsCount = 50;
        return solveForGivenCountOfSteps(data, stepsCount);
    }

    private long solveForGivenCountOfSteps(String data, int stepsCount) {
        String[] input = data.split("\n\n");
        String enhancement = input[0];

        var imageLines = input[1].split("\n");

        Set<Coords> map = new HashSet<>();
        for (int i = 0; i < imageLines.length; i++) {
            var chars = imageLines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '#') {
                    map.add(new Coords(i, j));
                }
            }
        }
        printMap(map);
        boolean previousInvert = false;
        boolean shouldInvert = enhancement.charAt(0) == '#';
        for (int i = 0; i < stepsCount; i++) {
            Set<Coords> enhanced = doEnhancement(enhancement, map, shouldInvert, previousInvert);
            printMap(enhanced);
            map = new HashSet<>(enhanced);
            if (enhancement.charAt(0) == '#') {
                previousInvert = shouldInvert;
                shouldInvert = !shouldInvert;
            }
        }

        return map.size();
    }

    private void printMap(Set<Coords> map) {

        StringBuilder sb = new StringBuilder();
        var minX = map.stream().mapToInt(Coords::getI).min().orElse(-1);
        var maxX = map.stream().mapToInt(Coords::getI).max().orElse(-1);
        var minY = map.stream().mapToInt(Coords::getJ).min().orElse(-1);
        var maxY = map.stream().mapToInt(Coords::getJ).max().orElse(-1);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {

                sb.append(map.contains(new Coords(x, y)) ? "#" : '.');

            }
            sb.append("\n");
        }

        log.debug("Map:\n{}", sb);
    }


    private Set<Coords> doEnhancement(String enhancement, Set<Coords> map, boolean shouldInvert, boolean previousInvert) {
        var minX = map.stream().mapToInt(Coords::getI).min().orElse(-1);
        var maxX = map.stream().mapToInt(Coords::getI).max().orElse(-1);
        var minY = map.stream().mapToInt(Coords::getJ).min().orElse(-1);
        var maxY = map.stream().mapToInt(Coords::getJ).max().orElse(-1);

        Set<Coords> result = new HashSet<>();

        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {

                int surrounding = surroundingToNumber(map, new Coords(x, y), previousInvert);

                var lit = enhancement.charAt(surrounding) == '#';
                if ((lit && !shouldInvert) || (!lit && shouldInvert)) {
                    result.add(new Coords(x, y));
                }

            }
        }


        return result;
    }

    private int surroundingToNumber(Set<Coords> map, Coords coords, boolean inverse) {

        int val1 = inverse ? 0 : 1;
        int val0 = inverse ? 1 : 0;

        int result = 0;
        int magnitude = 256;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                result += (map.contains(coords.plus(i, j)) ? val1 : val0) * magnitude;
                magnitude /= 2;
            }
        }
        return result;
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
