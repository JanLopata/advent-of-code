package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day24 extends Day {
    public static void main(String[] args) throws IOException {
        new Day24().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        Set<Hex> blackHexes = new HashSet<>();

        calculateBlackHexesPosition(input, blackHexes);

        return (long) blackHexes.size();
    }

    private void calculateBlackHexesPosition(String[] input, Set<Hex> blackHexes) {
        for (String s : input) {

            String currentString = s;
            Hex currentHex = new Hex(0, 0);

            while (!currentString.isEmpty()) {

                int maxSize = currentString.length() > 1 ? 2 : 1;

                for (int usedSize = maxSize; usedSize > 0; usedSize--) {

                    Hex work = currentHex.getHexInDirection(currentString.substring(0, usedSize));
                    if (work != null) {

                        currentString = currentString.substring(usedSize);
                        currentHex = work;
                        break;
                    }

                }

            }

            if (blackHexes.contains(currentHex)) {
                blackHexes.remove(currentHex);
            } else {
                blackHexes.add(currentHex);
            }

        }
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        Set<Hex> blackHexes = new HashSet<>();
        calculateBlackHexesPosition(input, blackHexes);


        log.info("Day: {}\t black tiles count: {}", 0, blackHexes.size());
        for (int i = 1; i <= 100; i++) {
            calculateDayChanges(blackHexes);
            log.info("Day: {}\t black tiles count: {}", i, blackHexes.size());
        }

        return (long) blackHexes.size();
    }

    private void calculateDayChanges(Set<Hex> blackHexes) {

        final Set<Hex> interestingHexes = blackHexes.stream()
                .flatMap(Hex::getAdjacentHexesStream)
                .collect(Collectors.toSet());

        interestingHexes.addAll(blackHexes);

        Set<Hex> newBlackHexes = new HashSet<>();

        for (Hex hex : interestingHexes) {

            long adjacentBlackCount = hex.getAdjacentHexesStream().filter(blackHexes::contains).count();

            if (blackHexes.contains(hex)) {

                // black hex - should we flip to white?
                boolean keepsBlack = true;
                if (adjacentBlackCount == 0 || adjacentBlackCount > 2)
                    keepsBlack = false;

                if (keepsBlack)
                    newBlackHexes.add(hex);

            } else {

                // white hex - should we flip to black?
                if (adjacentBlackCount == 2)
                    newBlackHexes.add(hex);

            }

        }

        blackHexes.clear();
        blackHexes.addAll(newBlackHexes);

    }


    @AllArgsConstructor
    @ToString
    @EqualsAndHashCode
    private static class Hex {

        int i;
        int j;

        public Hex getHexInDirection(String direction) {

            if ("e".equals(direction)) {
                return new Hex(i, j + 1);
            }

            if ("se".equals(direction)) {
                return new Hex(i + 1, j);
            }

            if ("sw".equals(direction)) {
                return new Hex(i + 1, j - 1);
            }

            if ("w".equals(direction)) {
                return new Hex(i, j - 1);
            }

            if ("nw".equals(direction)) {
                return new Hex(i - 1, j);
            }

            if ("ne".equals(direction)) {
                return new Hex(i - 1, j + 1);
            }

            return null;

        }

        public Stream<Hex> getAdjacentHexesStream() {

            return Stream.of("e", "se", "sw", "w", "nw", "ne")
                    .map(this::getHexInDirection);

        }

    }


    @Override
    public int getDayNumber() {
        return 24;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
