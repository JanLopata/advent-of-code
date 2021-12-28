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
import java.util.List;
import java.util.regex.Pattern;
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
        return solve(data, 50);
    }

    @Override
    public Object part2(String data) {
        return solve(data, Long.MAX_VALUE);
    }

    private long solve(String data, long rangeLimit) {
        String[] input = data.split("\n");
        List<Cuboid> inputCuboids = new ArrayList<>();
        for (String s : input) {
            var cuboid = parseCuboid(s);
            if (cuboid.getVertices()
                    .stream().flatMap(it -> Stream.of(it.getX(), it.getY(), it.getZ()))
                    .mapToLong(Math::abs).max().orElse(-1) <= rangeLimit)
                inputCuboids.add(cuboid);
        }

        List<Cuboid> knownCuboids = new ArrayList<>();
        knownCuboids.add(inputCuboids.get(0));

        for (int i = 1; i < inputCuboids.size(); i++) {

            log.info("Progress: {}/{}, cuboids: {}", i, inputCuboids.size(), knownCuboids.size());
            var next = inputCuboids.get(i);

            List<Cuboid> intersectionCuboids = new ArrayList<>();

            for (Cuboid knownCuboid : knownCuboids) {
                var newIntersection = knownCuboid.negativeIntersection(next);
                if (newIntersection.isValid())
                    intersectionCuboids.add(newIntersection);
            }

            knownCuboids.add(next);
            knownCuboids.addAll(intersectionCuboids);

        }

        return knownCuboids.stream()
                .mapToLong(it -> it.getValue() * it.volume())
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
                    ), isOn ? 1 : 0);
        }

        throw new IllegalArgumentException("not parsed");
    }


    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class Cuboid {
        private final Coord3 min;
        private final Coord3 max;
        private final int value;

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

        public long volume() {
            return (max.getX() - min.getX() + 1L)
                    * (max.getY() - min.getY() + 1L)
                    * (max.getZ() - min.getZ() + 1L);
        }

        public boolean isValid() {
            return min.getX() <= max.getX() && min.getY() <= max.getY() && min.getZ() <= max.getZ();
        }

        public Cuboid negativeIntersection(Cuboid another) {

            Coord3 intersectionMin = Coord3.of(
                    Math.max(this.min.getX(), another.min.getX()),
                    Math.max(this.min.getY(), another.min.getY()),
                    Math.max(this.min.getZ(), another.min.getZ())
            );
            Coord3 intersectionMax = Coord3.of(
                    Math.min(this.max.getX(), another.max.getX()),
                    Math.min(this.max.getY(), another.max.getY()),
                    Math.min(this.max.getZ(), another.max.getZ())
            );

            return new Cuboid(intersectionMin, intersectionMax, -this.value);
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
