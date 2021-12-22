package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coord3;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day22 extends Day {
    public static void main(String[] args) throws IOException {
        new Day22().printParts();
    }

    private static final Pattern PATTERN = Pattern.compile(
            "([^ ]*) x=(-?[0-9]+)..(-?[0-9]+),y=(-?[0-9]+)..(-?[0-9]+),z=(-?[0-9]+)..(-?[0-9]+)");

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        List<Cuboid> inputCuboids = new ArrayList<>();
        for (String s : input) {
            inputCuboids.add(parseCuboid(s));
        }

        Set<Cuboid> knownCuboids = new HashSet<>();
        knownCuboids.add(inputCuboids.get(0));

        for (int i = 1; i < inputCuboids.size(); i++) {

            log.info("Progress: {}/{}, cuboids: {}", i, inputCuboids.size(), knownCuboids.size());
            var next = inputCuboids.get(i);

            var vertices = next.getVertices();
            for (Coord3 vertex : vertices) {

                Set<Cuboid> temp = new HashSet<>();
                for (Cuboid knownCuboid : knownCuboids) {
                    temp.addAll(knownCuboid.breakDownAround(vertex));
                }
                knownCuboids.clear();
                knownCuboids.addAll(temp);
            }
            knownCuboids.removeIf(it -> it.isContainedIn(next));
            knownCuboids.add(next);
        }

        return knownCuboids.stream()
                .filter(it -> it.state)
                .mapToLong(Cuboid::volume)
                .sum();
    }

    private Cuboid parseCuboid(String s) {

        log.info("parsing: {}", s);
        var matcher = PATTERN.matcher(s);
        if (matcher.find()) {
            boolean isOn = "on".equals(matcher.group(1));

            return new Cuboid(
                    Coord3.of(
                            Integer.parseInt(matcher.group(2)),
                            Integer.parseInt(matcher.group(4)),
                            Integer.parseInt(matcher.group(6))),
                    Coord3.of(
                            Integer.parseInt(matcher.group(3)),
                            Integer.parseInt(matcher.group(5)),
                            Integer.parseInt(matcher.group(7))
                    ), isOn);
        }

        throw new IllegalArgumentException("not parsed");
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode(exclude = "state")
    @ToString
    private static class Cuboid {
        private final Coord3 min;
        private final Coord3 max;
        private final boolean state;

        List<Coord3> getVertices() {

            List<Coord3> result = new ArrayList<>(8);
            result.add(min);
            result.add(Coord3.of(max.getX(), min.getY(), min.getZ()));
            result.add(Coord3.of(min.getX(), max.getY(), min.getZ()));
            result.add(Coord3.of(max.getX(), max.getY(), min.getZ()));
            result.add(Coord3.of(min.getX(), min.getY(), max.getZ()));
            result.add(Coord3.of(max.getX(), min.getY(), max.getZ()));
            result.add(Coord3.of(min.getX(), max.getY(), max.getZ()));
            result.add(max);
            return result;

        }

        Set<Cuboid> breakDownAround(Coord3 vertex) {

            log.debug("breaking {} around {}", this, vertex);

            if (vertex.getX() < min.getX() || vertex.getY() < min.getY() || vertex.getZ() < min.getZ())
                return Set.of(this);
//            if (vertex.getX() > max.getX() || vertex.getY() > max.getY() || vertex.getZ() > max.getZ())
//                return Set.of(this);

            return Stream.of(this)
                    .flatMap(c -> c.slice(2, vertex.getZ()).stream())
                    .flatMap(c -> c.slice(1, vertex.getY()).stream())
                    .flatMap(c -> c.slice(0, vertex.getX()).stream())
                    .filter(Cuboid::isValid)
                    .collect(Collectors.toSet());
        }

        Set<Cuboid> slice(int dim, int value) {

            if (dim == 2) {
                int cornerMin = min.getZ();
                int cornerMax = max.getZ();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(max.getX(), max.getY(), value - 1), this.state);
                var secondSlice = new Cuboid(
                        Coord3.of(min.getX(), min.getY(), value),
                        Coord3.of(max.getX(), max.getY(), value),
                        this.state);
                var thirdSlice = new Cuboid(Coord3.of(min.getX(), min.getY(), value + 1), max, this.state);
                return checkSlices(firstSlice, secondSlice, thirdSlice);
            }

            if (dim == 1) {
                int cornerMin = min.getY();
                int cornerMax = max.getY();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(max.getX(), value - 1, max.getZ()), this.state);
                var secondSlice = new Cuboid(
                        Coord3.of(min.getX(), value, min.getZ()),
                        Coord3.of(max.getX(), value, max.getZ()),
                        this.state);
                var thirdSlice = new Cuboid(Coord3.of(min.getX(), value + 1, min.getZ()), max, this.state);
                return checkSlices(firstSlice, secondSlice, thirdSlice);
            }

            if (dim == 0) {
                int cornerMin = min.getX();
                int cornerMax = max.getX();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(value - 1, max.getY(), max.getZ()), this.state);
                var secondSlice = new Cuboid(
                        Coord3.of(value, min.getY(), min.getZ()),
                        Coord3.of(value, max.getY(), max.getZ()),
                        this.state);
                var thirdSlice = new Cuboid(Coord3.of(value + 1, min.getY(), min.getZ()), max, this.state);
                return checkSlices(firstSlice, secondSlice, thirdSlice);
            }


            throw new IllegalArgumentException("unknown dimension " + dim);
        }

        private Set<Cuboid> checkSlices(Cuboid firstSlice, Cuboid secondSlice, Cuboid thirdSlice) {
            return Stream.of(firstSlice, secondSlice, thirdSlice)
                    .filter(Cuboid::isValid)
                    .collect(Collectors.toSet());
        }

        private boolean outOfRangeForSlicing(int value, int cornerMin, int cornerMax) {
            return cornerMin > value || cornerMax < value;
        }

        public long volume() {
            return (max.getX() - min.getX() + 1L)
                    * (max.getY() - min.getY() + 1L)
                    * (max.getZ() - min.getZ() + 1L);
        }

        public boolean isValid() {
            return min.getX() <= max.getX() && min.getY() <= max.getY() && min.getZ() <= max.getZ();
        }

        boolean contains(Coord3 vertex) {

            if (vertex.getX() < min.getX())
                return false;
            if (vertex.getX() > max.getX())
                return false;

            if (vertex.getY() < min.getY())
                return false;
            if (vertex.getY() > max.getY())
                return false;

            if (vertex.getZ() < min.getZ())
                return false;
            if (vertex.getZ() > max.getZ())
                return false;

            return true;
        }

        public boolean isContainedIn(Cuboid next) {

            if (this.min.getX() < next.getMin().getX())
                return false;
            if (this.min.getY() < next.getMin().getY())
                return false;
            if (this.min.getZ() < next.getMin().getZ())
                return false;

            if (this.max.getX() > next.getMax().getX())
                return false;
            if (this.max.getY() > next.getMax().getY())
                return false;
            if (this.max.getZ() > next.getMax().getZ())
                return false;

            log.debug("{} is contained in {}", this, next);
            return true;
        }

        public List<Coord3> getAllVertices() {
            List<Coord3> result = new ArrayList<>();
            for (int x = min.getX(); x <= max.getX(); x++) {
                for (int y = min.getY(); y <= max.getY(); y++) {
                    for (int z = min.getZ(); z <= max.getZ(); z++) {
                        result.add(Coord3.of(x, y, z));
                    }
                }
            }
            return result;

        }

    }

    @Override
    public int getDayNumber() {
        return 22;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
