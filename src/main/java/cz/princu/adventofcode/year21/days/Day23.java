package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coords;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day23 extends Day {
    public static void main(String[] args) throws IOException {
        new Day23().printParts();
    }

    private static final String TARGET = "#############\n" +
            "#...........#\n" +
            "###A#B#C#D###\n" +
            "  #A#B#C#D#\n" +
            "  #########";

    private static final Map<Character, Integer> MOVEMENT_COST_MAP;

    private static final int BOTTOM_HOME_LEVEL = 3;

    static {
        MOVEMENT_COST_MAP = new HashMap<>();
        MOVEMENT_COST_MAP.put('A', 1);
        MOVEMENT_COST_MAP.put('B', 10);
        MOVEMENT_COST_MAP.put('C', 100);
        MOVEMENT_COST_MAP.put('D', 1000);

    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        char[][] charArray = convertInputToChars(input);

        List<Target> targets = findTargets(charArray);
        var optionsFromStart = findOptions(charArray, targets);

        Map<Map<Amphipod, Target>, Long> memory = new HashMap<>();
        Map<Amphipod, Target> situation = new HashMap<>();
        for (int column = 0; column < charArray.length; column++) {
            for (int row = 0; row < charArray[column].length; row++) {
                var c = charArray[column][row];
                if (Character.isUpperCase(c)) {
                    Coords coords = new Coords(column, row);
                    var position = findTargetFromCoordinates(targets).apply(coords).findAny().orElseThrow();
                    var isHome = row == BOTTOM_HOME_LEVEL
                            && position.isAllowed(c);
                    var amphipod = Amphipod.builder()
                            .id(UUID.randomUUID().toString())
                            .isHome(isHome)
                            .moveHome(!isHome)
                            .moveToHall(!isHome)
                            .type(c)
                            .movementCost(MOVEMENT_COST_MAP.get(c))
                            .build();
                    var put = situation.put(amphipod, position);
                    if (put != null) {
                        log.error("overwrite at {} - {}", amphipod, position);
                        throw new IllegalStateException();
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Amphipod, Target> entry : situation.entrySet()) {
            sb.append(entry.getKey());
            sb.append("\n");
            sb.append(entry.getValue());
            sb.append("\n\n");
        }
        log.info("Start: \n{}", sb);

        AtomicLong globalMin = new AtomicLong(Long.MAX_VALUE);
        solve(globalMin, memory, situation, 0L, optionsFromStart);

        return globalMin.get();
    }

    private void solve(AtomicLong globalMin, Map<Map<Amphipod, Target>, Long> memory, Map<Amphipod, Target> situation, long price, HashMap<Target, List<TargetToTarget>> optionsFromStart) {

        if (memory.containsKey(situation)) {
            log.debug("cache event");
            if (memory.get(situation) > price) {
                memory.put(situation, price);
            }
            return;
        }

        if (price > globalMin.get()) {
            log.debug("price {} exceeded", globalMin.get());
            return;
        }

        if (isSolved(situation)) {
            log.info("solved with price {}", price);
            if (price < globalMin.get())
                globalMin.set(price);
            return;
        }

        var canMoveToHall = situation.keySet()
                .stream()
                .filter(it -> it.moveToHall)
                .collect(Collectors.toList());

        for (Amphipod amphipod : canMoveToHall) {
            var currentPosition = situation.get(amphipod);
            for (TargetToTarget targetToTarget : optionsFromStart.get(currentPosition)) {

                if (!targetToTarget.end.isHallway)
                    continue;

                if (!targetToTarget.isPassable(situation, amphipod))
                    continue;

                var newSituation = new HashMap<>(situation);
                newSituation.remove(amphipod);
                var newAmphipod = Amphipod.builder()
                        .id(amphipod.id)
                        .isHome(targetToTarget.end.restrictedTo != null)
                        .movementCost(amphipod.movementCost)
                        .moveToHall(false)
                        .moveHome(amphipod.moveHome)
                        .type(amphipod.type)
                        .build();
                newSituation.put(newAmphipod, targetToTarget.end);

                var movePrice = amphipod.movementCost * targetToTarget.distance;
                solve(globalMin, memory, newSituation, price + movePrice, optionsFromStart);

            }

        }

        var canMoveToHome = situation.keySet()
                .stream()
                .filter(it -> it.moveHome)
                .collect(Collectors.toList());

        for (Amphipod amphipod : canMoveToHome) {
            var currentPosition = situation.get(amphipod);
            for (TargetToTarget targetToTarget : optionsFromStart.get(currentPosition)) {

                if (targetToTarget.end.isHallway)
                    continue;

                if (!targetToTarget.isPassable(situation, amphipod))
                    continue;

                var newSituation = new HashMap<>(situation);
                newSituation.remove(amphipod);
                var newAmphipod = Amphipod.builder()
                        .id(amphipod.id)
                        .isHome(targetToTarget.end.restrictedTo != null)
                        .movementCost(amphipod.movementCost)
                        .moveToHall(false)
                        .moveHome(false)
                        .type(amphipod.type)
                        .build();
                newSituation.put(newAmphipod, targetToTarget.end);

                var movePrice = amphipod.movementCost * targetToTarget.distance;
                solve(globalMin, memory, newSituation, price + movePrice, optionsFromStart);

            }

        }

        memory.put(situation, price);

    }

    private boolean isSolved(Map<Amphipod, Target> situation) {

        return situation.keySet().stream().allMatch(it -> it.isHome);

    }

    private HashMap<Target, List<TargetToTarget>> findOptions(char[][] charArray, List<Target> targets) {
        HashMap<Target, List<TargetToTarget>> optionsFromStart = new HashMap<>();
        Graph<Coords, DefaultEdge> graph = initGraph(charArray);
        var dijkstraShortestPath = new DijkstraShortestPath<>(graph);

        for (Target start : targets) {

            optionsFromStart.put(start, new ArrayList<>());
            for (Target end : targets) {
                if (start.equals(end))
                    continue;

                var path = dijkstraShortestPath.getPath(start.coords, end.coords);
                var pathCoordsBetween = path.getVertexList().stream()
                        .filter(it -> !it.equals(start.coords))
                        .flatMap(findTargetFromCoordinates(targets))
                        .collect(Collectors.toList());

                optionsFromStart.get(start).add(
                        new TargetToTarget(start, end, path.getLength(), pathCoordsBetween));
            }
        }
        return optionsFromStart;
    }

    private Function<Coords, Stream<? extends Target>> findTargetFromCoordinates(List<Target> targets) {
        return c -> targets.stream().filter(it -> it.coords.equals(c)).findAny().stream();
    }

    private Graph<Coords, DefaultEdge> initGraph(char[][] charArray) {
        Graph<Coords, DefaultEdge> resultGraph = new SimpleGraph<>(DefaultEdge.class);

        for (int column = 0; column < charArray.length; column++) {
            for (int row = 0; row < charArray[column].length; row++) {

                var c = charArray[column][row];
                if (isWalkable(c)) {
                    var coords = new Coords(column, row);
                    resultGraph.addVertex(coords);
                    ifWalkableAddEdge(resultGraph, charArray, coords, -1, 0);
                    ifWalkableAddEdge(resultGraph, charArray, coords, 0, -1);
                }
            }
        }
        return resultGraph;
    }

    private boolean isWalkable(char c) {
        return c != '#' && c != ' ';
    }

    private void ifWalkableAddEdge(Graph<Coords, DefaultEdge> resultGraph, char[][] charArray, Coords v, int dx, int dy) {

        if (!resultGraph.containsVertex(v))
            return;

        var next = v.plus(dx, dy);
        if (!isWalkable(charArray[next.getI()][next.getJ()]))
            return;

        resultGraph.addEdge(v, next);
    }

    private List<Target> findTargets(char[][] a) {

        List<Target> result = new ArrayList<>();

        for (int col = 0; col < a.length; col++) {
            for (int row = 0; row < a[col].length; row++) {

                // hallway left/right
                if (a[col][row] == '.' && a[col][row - 1] == '#'
                        && a[col + 1][row - 1] == '#'
                        && a[col][row + 1] == '#'
                        && a[col + 1][row + 1] == '#'
                ) {
                    if (a[col - 1][row] == '#') {
                        result.add(Target.of(col, row, null, true));
                        result.add(Target.of(col + 1, row, null, true));
                        continue;
                    } else if (a[col + 1][row] == '#') {
                        result.add(Target.of(col, row, null, true));
                        result.add(Target.of(col - 1, row, null, true));
                        continue;
                    }
                }

                if (Character.isUpperCase(a[col][row])
                        && Character.isUpperCase(a[col + 2][row])
                        && a[col + 1][row - 1] == '.') {
                    result.add(Target.of(col + 1, row - 1, null, true));
                }

                if (Character.isUpperCase(a[col][row])) {
                    result.add(Target.of(col, row, columnToType(col), false));
                }
            }
        }

        return result;
    }

    private char columnToType(int col) {
        return (char) (65 + (col - 3) / 2);
    }

    private char[][] convertInputToChars(String[] input) {
        char[][] charArray = new char[input[0].length()][];
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = new char[input.length];
            Arrays.fill(charArray[i], ' ');
        }

        for (int row = 0; row < input.length; row++) {
            var chars = input[row].toCharArray();
            for (int column = 0; column < chars.length; column++) {
                charArray[column][row] = chars[column];
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < charArray[0].length; row++) {
            for (char[] chars : charArray) {
                sb.append(chars[row]);
            }
            sb.append("\n");
        }
        log.info("\n{}", sb);

        return charArray;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor
    @Builder
    private static class Amphipod {
        final String id;
        final boolean moveToHall;
        final boolean moveHome;
        final int movementCost;
        final char type;
        final boolean isHome;
    }

    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor
    private static class Target {

        final Coords coords;
        final Character restrictedTo;
        final boolean isHallway;

        boolean isAllowed(char type) {
            return restrictedTo == null || restrictedTo == type;
        }

        public static Target of(int x, int y, Character restrictedTo, boolean isHallway) {
            return new Target(new Coords(x, y), restrictedTo, isHallway);
        }

    }

    @EqualsAndHashCode
    @ToString
    @RequiredArgsConstructor
    private static class TargetToTarget {

        final Target start;
        final Target end;
        final int distance;
        final List<Target> targetsMet;

        //        boolean isPassable(Map<Target, Amphipod> amphipodMap, Amphipod amphipod) {
//
//            return !amphipodMap.containsKey(end)
//                    && end.isAllowed(amphipod.type)
//                    && targetsMet.stream().noneMatch(amphipodMap::containsKey);
//        }
//
        boolean isPassable(Map<Amphipod, Target> amphipodTargetMap, Amphipod amphipod) {

            return !amphipodTargetMap.containsValue(end)
                    && end.isAllowed(amphipod.type)
                    && targetsMet.stream().noneMatch(amphipodTargetMap::containsValue);

        }

    }


    @Override
    public int getDayNumber() {
        return 23;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
