package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day1 extends Day {
    public static void main(String[] args) throws IOException {
        new Day1().printParts();
    }

    @Override
    public Object part1(String data) {

        List<Long> values = getValues(data);
        Set<Long> valuesSet = new HashSet<>(values);

        for (Long firstValue : values) {

            long complement = 2020L - firstValue;

            if (valuesSet.contains(complement))
                return firstValue * complement;
        }

        return 0L;
    }

    @Override
    public Object part2(String data) {

        List<Long> values = getValues(data);
        Set<Long> valuesSet = new HashSet<>(values);

        for (Long v1 : values) {
            for (Long v2 : values) {

                long complement = 2020L - v1 - v2;

                if (valuesSet.contains(complement))
                    return v1 * v2 * complement;
            }
        }

        return 0L;
    }

    private List<Long> getValues(String data) {
        return Arrays.stream(data.split("\n")).map(Long::parseLong).collect(Collectors.toList());
    }

    @Override
    public int getDayNumber() {
        return 1;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
