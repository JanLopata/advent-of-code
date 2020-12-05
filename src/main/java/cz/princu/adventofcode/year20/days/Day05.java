package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class Day05 extends Day {
    public static void main(String[] args) throws IOException {
        new Day05().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");
        return Arrays.stream(lines).mapToLong(this::getId).max().orElse(0L);
    }

    private Long getId(String bp) {

        Long row = getRow(bp);
        Long column = getColumn(bp);
        return row * 8 + column;
    }

    private Long getRow(String bp) {
        String input = bp.substring(0, 7);
        String binaryString = input.replace('F', '0').replace('B', '1');
        return Long.parseLong(binaryString, 2);
    }

    private Long getColumn(String bp) {
        String input = bp.substring(7, 10);
        String binaryString = input.replace('L', '0').replace('R', '1');
        return Long.parseLong(binaryString, 2);
    }


    @Override
    public Object part2(String data) {

        String[] lines = data.split("\n");

        Set<Long> allIds = Arrays.stream(lines).map(this::getId).collect(Collectors.toSet());

        long min = allIds.stream().mapToLong(it -> it).min().orElse(0L);
        long max = allIds.stream().mapToLong(it -> it).max().orElse(0L);

        for (long i = min; i <= max; i++) {
            if (!allIds.contains(i))
                return i;
        }

        return 0L;
    }

    @Override
    public int getDayNumber() {
        return 5;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
