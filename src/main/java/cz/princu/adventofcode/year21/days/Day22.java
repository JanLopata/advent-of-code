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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day22 extends Day {
    public static void main(String[] args) throws IOException {
        new Day22().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");


        var start = new Cuboid(Coord3.of(0, 0, 0), Coord3.of(10, 10, 10));

        var broken = start.breakDownAround(Coord3.of(10, 0, 3));


        return 0L;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Getter
    @AllArgsConstructor
    @EqualsAndHashCode
    @ToString
    private static class Cuboid {
        private final Coord3 min;
        private final Coord3 max;

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

            return Stream.of(this)
                    .flatMap(c -> c.slice(2, vertex.getZ()).stream())
                    .flatMap(c -> c.slice(1, vertex.getY()).stream())
                    .flatMap(c -> c.slice(0, vertex.getX()).stream())
                    .collect(Collectors.toSet());
        }

        Set<Cuboid> slice(int dim, int value) {

            if (dim == 2) {
                int cornerMin = min.getZ();
                int cornerMax = max.getZ();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(max.getX(), max.getY(), value));
                var secondSlice = new Cuboid(Coord3.of(min.getX(), min.getY(), value + 1), max);
                return Set.of(firstSlice, secondSlice);
            }

            if (dim == 1) {
                int cornerMin = min.getY();
                int cornerMax = max.getY();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(max.getX(), value, max.getZ()));
                var secondSlice = new Cuboid(Coord3.of(min.getX(), value + 1, min.getZ()), max);
                return Set.of(firstSlice, secondSlice);
            }

            if (dim == 0) {
                int cornerMin = min.getX();
                int cornerMax = max.getX();
                if (outOfRangeForSlicing(value, cornerMin, cornerMax)) {
                    return Set.of(this);
                }
                var firstSlice = new Cuboid(min, Coord3.of(value, max.getY(), max.getZ()));
                var secondSlice = new Cuboid(Coord3.of(value + 1, min.getY(), min.getZ()), max);
                return Set.of(firstSlice, secondSlice);
            }


            throw new IllegalArgumentException("unknown dimension " + dim);
        }

        private boolean outOfRangeForSlicing(int value, int cornerMin, int cornerMax) {
            return cornerMin >= value || cornerMax <= value;
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
