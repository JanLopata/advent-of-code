package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day01 extends Day {
    public static void main(String[] args) throws IOException {
        new Day01().printParts();
    }

    @Override
    public Object part1(String data) {

        List<Long> values = getValues(data);

        long result = 0;

        Long prev = values.get(0);
        for (Long value : values) {

            if (prev < value)
                result++;
            prev = value;
        }

        return result;
    }

    @Override
    public Object part2(String data) {

        List<Long> values = getValues(data);

        int windowSize = 3;
        long result = 0;
        long prevSum = Long.MAX_VALUE;

        for (int i = 0; i < values.size() - windowSize + 1; i++) {
            Long sum = 0L;
            for (int j = 0; j < windowSize; j++) {
                sum += values.get(i + j);
            }
            if (prevSum < sum)
                result++;
            prevSum=sum;
        }

        return result;
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
        return 2021;
    }
}
