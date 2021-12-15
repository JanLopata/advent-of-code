package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.ArrayIndexChecker;
import cz.princu.adventofcode.common.utils.Coords;
import cz.princu.adventofcode.common.utils.DataArrayUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day15 extends Day {
    public static void main(String[] args) throws IOException {
        new Day15().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");
        var enterRisk = DataArrayUtils.parseDataArray(input);

        AtomicLong minRisk = new AtomicLong((long) enterRisk.length * enterRisk[0].length * 9);
        LinkedList<TravelProgress> travel = new LinkedList<>();
        Set<Coords> visited = new HashSet<>();
        var start = new Coords(0, 0);
        travel.add(new TravelProgress(start, 0));
        visited.add(start);
        var end = new Coords(enterRisk.length - 1, enterRisk[0].length - 1);
        ArrayIndexChecker checker = new ArrayIndexChecker(enterRisk.length, enterRisk[0].length);

        transverse(enterRisk, travel, visited, checker, minRisk, end);

        return minRisk.get();
    }

    private void transverse(int[][] enterRisk,
                            LinkedList<TravelProgress> travel,
                            Set<Coords> visited,
                            ArrayIndexChecker checker,
                            AtomicLong minRisk,
                            Coords end) {

        var current = travel.getLast();
        if (current.getCoords().equals(end)) {

            minRisk.set(Math.min(minRisk.get(), current.getAcummulatedRisk()));
            return;

        }

        if (current.getAcummulatedRisk() >= minRisk.get()) {
            return;
        }

        var hungry = getHungry(current.getCoords(), enterRisk, checker, visited);

        for (Pair<Coords, Integer> coordsIntegerPair : hungry) {

            checkAndProgress(coordsIntegerPair.getLeft(), enterRisk, travel, visited, checker, minRisk, end);
        }


    }

    private List<Pair<Coords, Integer>> getHungry(Coords current, int[][] enterRisk, ArrayIndexChecker checker, Set<Coords> visited) {

        List<Pair<Coords, Integer>> result = new ArrayList<>();
        for (Coords coords : navigation) {
            var next = current.plus(coords);

            if (!checker.isOutOfBounds(next)) {
                result.add(Pair.of(next, enterRisk[next.getI()][next.getJ()]));
            }
        }

        result.sort(Comparator.comparingInt(Pair::getRight));

        return result;
    }

    private void checkAndProgress(Coords next, int[][] enterRisk, LinkedList<TravelProgress> travel, Set<Coords> visited, ArrayIndexChecker checker, AtomicLong minRisk, Coords end) {

        log.debug("Day15::checkAndProgress: {}", travel);
        if (visited.contains(next)) {
            return;
        }

        travel.add(new TravelProgress(next, travel.getLast().getAcummulatedRisk()
                + enterRisk[next.getI()][next.getJ()]));
        visited.add(next);

        transverse(enterRisk, travel, visited, checker, minRisk, end);

        travel.removeLast();
        visited.remove(next);

    }

    private static final List<Coords> navigation = Stream.of(new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)).collect(Collectors.toUnmodifiableList());

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    @RequiredArgsConstructor
    @ToString
    @Getter
    private static class TravelProgress {
        private final Coords coords;
        private final long acummulatedRisk;
    }


    @Override
    public int getDayNumber() {
        return 15;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
