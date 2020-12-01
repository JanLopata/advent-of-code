package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 extends Day {
    public static void main(String[] args) throws IOException {
        new Day1().printParts();
    }

    @Override
    public Object part1(String data) {

        List<Long> values = getValues(data);

        for (int first = 0; first < values.size(); first++) {

            for (int second = 0; second < values.size(); second++) {
                if (first == second)
                    continue;

                if (values.get(first) + values.get(second) == 2020L)
                    return values.get(first) * values.get(second);

            }
        }

        return 0L;
    }

    @Override
    public Object part2(String data) {

        List<Long> values = getValues(data);

        for (Long v1 : values) {
            for (Long v2 : values) {
                for (Long v3 : values) {
                    if (v1 + v2 + v3 == 2020L)
                        return v1 * v2 * v3;
                }
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
