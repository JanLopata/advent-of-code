package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Day24 extends Day {
    public static void main(String[] args) throws IOException {
        new Day24().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        Set<Hex> blackHexes = new HashSet<>();

        for (String s : input) {

            String currentString = s;
            Hex currentHex = new Hex(0, 0);

            while (!currentString.isEmpty()) {

                int maxSize = currentString.length() > 1 ? 2 : 1;

                for (int usedSize = maxSize; usedSize > 0; usedSize--) {

                    Hex work = currentHex.getHexInDirection(currentString.substring(0, usedSize));
                    if (work == null)
                        continue;

                    currentString = currentString.substring(usedSize);
                    currentHex = work;
                    break;

                }


            }

            if (blackHexes.contains(currentHex)) {
                blackHexes.remove(currentHex);
            } else {
                blackHexes.add(currentHex);
            }

        }

        return (long) blackHexes.size();
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
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
