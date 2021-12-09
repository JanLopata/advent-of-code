package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day09 extends Day {
    public static void main(String[] args) throws IOException {
        new Day09().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        int[][] dataArray = getDataArray(input);

        var lowPoints = findLowPoints(dataArray);
        return lowPoints.stream().mapToLong(it -> dataArray[it.i][it.j] + 1).sum();

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        int[][] dataArray = getDataArray(input);

        var lowPoints = findLowPoints(dataArray);

        ArrayIndexChecker checker = new ArrayIndexChecker(dataArray.length, dataArray[0].length);

        List<Long> basinSizes = new ArrayList<>();
        for (Coords lowPoint : lowPoints) {
            long basinSize = getBasinSize(checker, dataArray, lowPoint);
            basinSizes.add(basinSize);
        }

        var threeLargest = basinSizes.stream().sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
        long result = 1;
        for (Long aLong : threeLargest) {
            result *= aLong;
        }

        return result;
    }


    private int[][] getDataArray(String[] input) {
        int[][] dataArray = new int[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            var split = input[i].split("");
            for (int j = 0; j < split.length; j++) {
                dataArray[i][j] = Integer.parseInt(split[j]);
            }
        }
        return dataArray;
    }

    private List<Coords> findLowPoints(int[][] dataArray) {

        List<Coords> result = new ArrayList<>();
        ArrayIndexChecker checker = new ArrayIndexChecker(dataArray.length, dataArray[0].length);

        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                int minAdjacent = Integer.MAX_VALUE;

                minAdjacent = updateFromAdjacent(minAdjacent, dataArray, checker, i + 1, j);
                minAdjacent = updateFromAdjacent(minAdjacent, dataArray, checker, i - 1, j);
                minAdjacent = updateFromAdjacent(minAdjacent, dataArray, checker, i, j + 1);
                minAdjacent = updateFromAdjacent(minAdjacent, dataArray, checker, i, j - 1);

                if (minAdjacent > dataArray[i][j])
                    result.add(new Coords(i, j));

            }

        }

        return result;
    }

    private int updateFromAdjacent(int minAdjacent, int[][] dataArray, ArrayIndexChecker checker, int i, int j) {

        if (checker.isOutOfBounds(i, j))
            return minAdjacent;

        return Math.min(minAdjacent, dataArray[i][j]);

    }


    private long getBasinSize(ArrayIndexChecker checker, int[][] dataArray, Coords lowest) {

        Set<Coords> basinElements = new HashSet<>();
        findBasinPoints(checker, dataArray, basinElements, lowest);
        return basinElements.size();

    }

    private void findBasinPoints(ArrayIndexChecker checker,
                                 int[][] dataArray,
                                 Set<Coords> basinElements,
                                 Coords lowest) {

        if (basinElements.contains(lowest)) {
            return;
        }

        if (checker.isOutOfBounds(lowest)) {
            return;
        }

        if (dataArray[lowest.i][lowest.j] >= 9) {
            return;
        }

        basinElements.add(lowest);

        findBasinPoints(checker, dataArray, basinElements, lowest.plus(1, 0));
        findBasinPoints(checker, dataArray, basinElements, lowest.plus(-1, 0));
        findBasinPoints(checker, dataArray, basinElements, lowest.plus(0, 1));
        findBasinPoints(checker, dataArray, basinElements, lowest.plus(0, -1));

    }

    @AllArgsConstructor
    static class ArrayIndexChecker {

        private final int maxI;
        private final int maxJ;

        boolean isOutOfBounds(int i, int j) {
            if (i < 0)
                return true;

            if (j < 0)
                return true;

            if (i >= maxI)
                return true;

            return j >= maxJ;
        }

        boolean isOutOfBounds(Coords coords) {
            return isOutOfBounds(coords.i, coords.j);
        }
    }

    @AllArgsConstructor
    @Getter
    @ToString
    @EqualsAndHashCode
    private static class Coords {

        final int i;
        final int j;

        public Coords plus(int i, int j) {
            return new Coords(this.i + i, this.j + j);
        }

    }


    @Override
    public int getDayNumber() {
        return 9;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
