package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day12 extends Day {
    public static void main(String[] args) throws IOException {
        new Day12().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        Set<Pair<String, String>> connections = new HashSet<>();
        for (String s : input) {

            var split = s.split("-");
            connections.add(Pair.of(split[0], split[1]));
            connections.add(Pair.of(split[1], split[0]));

        }

        HashMap<String, AtomicInteger> visitedCounter = new HashMap<>();


        AtomicLong pathCounter = new AtomicLong(0);

        var current = new LinkedList<String>();
        current.add("start");
        generatePaths(current, visitedCounter, pathCounter, connections);


        return pathCounter.get();
    }

    private void generatePaths(LinkedList<String> current, HashMap<String, AtomicInteger> visitedCounter, AtomicLong pathCounter, Set<Pair<String, String>> connections) {

        var last = current.getLast();
        if (Objects.equals(last, "end")) {
            log.info("Path: {}", current);
            pathCounter.incrementAndGet();
            return;
        }

        if (StringUtils.isAllLowerCase(last)) {
            if (visitedCounter.containsKey(last) && visitedCounter.get(last).get() > 0) {
                return;
            }
        }

        visitedCounter.computeIfAbsent(last, it -> new AtomicInteger(0));
        visitedCounter.get(last).incrementAndGet();


        // explore
        for (Pair<String, String> connection : connections) {

            if (!connection.getLeft().equals(last)) {
                continue;
            }

            var next = connection.getRight();

            current.add(next);
            generatePaths(current, visitedCounter, pathCounter, connections);
            current.removeLast();
        }

        visitedCounter.get(last).decrementAndGet();

    }


    private void generatePathsUpgradedVisits(LinkedList<String> current, HashMap<String, AtomicInteger> visitedCounter, Set<String> smallTwice, AtomicLong pathCounter, Set<Pair<String, String>> connections) {

        var last = current.getLast();
        if (Objects.equals(last, "end")) {
            log.info("Path: {}", String.join(",", current));
            pathCounter.incrementAndGet();
            return;
        }


        if (StringUtils.isAllLowerCase(last)) {

            if (visitedCounter.containsKey(last) && visitedCounter.get(last).get() > 0) {

                if (visitedCounter.get(last).get() == 1) {

                    if (last.equals("start")){
                        return;
                    }

                    boolean smallWasVisitedTwice = visitedCounter.entrySet().stream()
                            .filter(it -> StringUtils.isAllLowerCase(it.getKey()))
                            .anyMatch(it -> it.getValue().get() > 1);

                    if (smallWasVisitedTwice) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        visitedCounter.computeIfAbsent(last, it -> new AtomicInteger(0));
        visitedCounter.get(last).incrementAndGet();


        // explore
        for (Pair<String, String> connection : connections) {

            if (!connection.getLeft().equals(last)) {
                continue;
            }

            var next = connection.getRight();

            current.add(next);

            generatePathsUpgradedVisits(current, visitedCounter, smallTwice, pathCounter, connections);
            smallTwice.remove(next);
            current.removeLast();
        }

        visitedCounter.get(last).decrementAndGet();

    }


    @Override
    public Object part2(String data) {
        String[] input = data.split("\n");

        Set<Pair<String, String>> connections = new HashSet<>();
        for (String s : input) {

            var split = s.split("-");
            connections.add(Pair.of(split[0], split[1]));
            connections.add(Pair.of(split[1], split[0]));

        }

        HashMap<String, AtomicInteger> visitedCounter = new HashMap<>();

        AtomicLong pathCounter = new AtomicLong(0);

        var current = new LinkedList<String>();
        current.add("start");
        Set<String> smallVisit = new HashSet<>(1);
        generatePathsUpgradedVisits(current, visitedCounter, smallVisit, pathCounter, connections);


        return pathCounter.get();
    }


    @Override
    public int getDayNumber() {
        return 12;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
