package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day05 extends Day {
    public static void main(String[] args) throws IOException {
        new Day05().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        return solveVulcanos(input, false);
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return solveVulcanos(input, true);
    }

    private Long solveVulcanos(String[] input, boolean diagonalsAllowed) {
        var lines = Arrays.stream(input).map(Line::parseLine).collect(Collectors.toList());

        Map<Pair<Integer, Integer>, Integer> vulcanosGrid = new HashMap<>();

        for (Line line : lines) {
            var drawn = line.drawLine(vulcanosGrid, diagonalsAllowed);
            if (drawn) {
                log.debug(line.toString());
                drawMap(vulcanosGrid);
            }
        }

        drawMap(vulcanosGrid);


        return vulcanosGrid.values().stream().filter(it -> it >= 2).count();
    }

    private void drawMap(Map<Pair<Integer, Integer>, Integer> vulcanosGrid) {

        if (!log.isDebugEnabled())
            return;

        int max = vulcanosGrid.keySet().stream()
                .flatMap(it -> Stream.of(it.getLeft(), it.getRight()))
                .mapToInt(it -> it)
                .max().orElse(0) + 1;

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < max; i++) {

            for (int j = 0; j < max; j++) {

                var pair = Pair.of(j, i);
                result.append(vulcanosGrid.getOrDefault(pair, 0));


            }
            result.append("\n");

        }
        log.debug("\n" + result);

    }


    @RequiredArgsConstructor
    @ToString
    public static class Line {

        private final int x1;
        private final int y1;
        private final int x2;
        private final int y2;

        public static Line parseLine(String lineString) {

            var leftRight = lineString.split(" -> ");

            return new Line(
                    Integer.parseInt(leftRight[0].split(",")[0]),
                    Integer.parseInt(leftRight[0].split(",")[1]),
                    Integer.parseInt(leftRight[1].split(",")[0]),
                    Integer.parseInt(leftRight[1].split(",")[1])
            );
        }

        public boolean drawLine(Map<Pair<Integer, Integer>, Integer> map, boolean diagonalsAllowed) {

            int minX = Math.min(x1, x2);
            int maxX = Math.max(x1, x2);
            int minY = Math.min(y1, y2);
            int maxY = Math.max(y1, y2);

            int diffX = x2 - x1;
            int diffY = y2 - y1;

            if (diffY * diffX != 0) {

                if (!diagonalsAllowed)
                    return false;

                int dx = maxX - minX;

                if (diffX * diffY > 0) {
                    for (int i = 0; i <= dx; i++) {
                        addToMap(minX + i, minY + i, map);
                    }
                } else {
                    for (int i = 0; i <= dx; i++) {
                        addToMap(minX + i, maxY - i, map);
                    }
                }

                return true;
            }

            if (diffX != 0) {
                drawHorizontal(map, minX, maxX);
            } else {
                drawVertical(map, minY, maxY);
            }
            return true;
        }

        private void drawVertical(Map<Pair<Integer, Integer>, Integer> map, int minY, int maxY) {
            for (int i = minY; i <= maxY; i++) {
                addToMap(x1, i, map);
            }
        }

        private void drawHorizontal(Map<Pair<Integer, Integer>, Integer> map, int minX, int maxX) {
            for (int i = minX; i <= maxX; i++) {
                addToMap(i, y1, map);
            }
        }

        private void addToMap(int i, int j, Map<Pair<Integer, Integer>, Integer> map) {

            var coords = Pair.of(i, j);

            if (map.containsKey(coords)) {
                var value = map.get(coords);
                map.put(coords, value + 1);
            } else {
                map.put(coords, 1);
            }

        }

    }


    @Override
    public int getDayNumber() {
        return 5;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
