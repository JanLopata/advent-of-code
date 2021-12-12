package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day12 extends Day {

    public static void main(String[] args) throws IOException {
        new Day12().printParts();
    }

    private static final String START = "start";

    @Override
    public Object part1(String data) {

       return solve(data, false).get();
    }

    @Override
    public Object part2(String data) {

        return solve(data, true).get();
    }

    private AtomicLong solve(String data, boolean smallRevisitAllowed) {
        Set<Pair<String, String>> connections = getConnections(data);
        Set<String> smallVisits = new HashSet<>();
        AtomicLong pathCounter = new AtomicLong(0);

        var current = new LinkedList<String>();
        current.add(START);
        generatePaths(current, smallVisits, smallRevisitAllowed, pathCounter, connections);
        return pathCounter;
    }

    private Set<Pair<String, String>> getConnections(String data) {
        String[] input = data.split("\n");

        Set<Pair<String, String>> connections = new HashSet<>();
        for (String s : input) {
            var split = s.split("-");
            connections.add(Pair.of(split[0], split[1]));
            connections.add(Pair.of(split[1], split[0]));
        }
        return connections;
    }

    private void generatePaths(LinkedList<String> current,
                               Set<String> smallVisited,
                               boolean smallRevisitAllowed,
                               AtomicLong pathCounter,
                               Set<Pair<String, String>> connections) {

        var last = current.getLast();
        if (Objects.equals(last, "end")) {
            log.debug("Path: {}", String.join(",", current));
            pathCounter.incrementAndGet();
            return;
        }

        boolean secondLastVisit = false;
        if (StringUtils.isAllLowerCase(last) && smallVisited.contains(last)) {
            if (last.equals(START)) {
                return;
            }

            if (smallRevisitAllowed) {
                smallRevisitAllowed = false;
                secondLastVisit = true;
            } else {
                return;
            }
        }
        smallVisited.add(last);

        // explore
        for (Pair<String, String> connection : connections) {

            if (!connection.getLeft().equals(last)) {
                continue;
            }

            var next = connection.getRight();
            current.add(next);
            generatePaths(current, smallVisited, smallRevisitAllowed, pathCounter, connections);
            current.removeLast();
        }

        if (!secondLastVisit) {
            smallVisited.remove(last);
        }

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
