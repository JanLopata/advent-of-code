package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day07 extends Day {
    public static void main(String[] args) throws IOException {
        new Day07().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split(",");

        List<Long> inputLong = Arrays.stream(input).map(Long::parseLong).collect(Collectors.toList());

        var max = Arrays.stream(input).mapToInt(Integer::parseInt).max().orElse(0);

        long minFuel = Long.MAX_VALUE;
        for (int i = 0; i < max + 1; i++) {

            long fuel = 0L;
            for (Long aLong : inputLong) {
                fuel += Math.abs(aLong - i);
            }

            log.info("{}: {}", i, fuel);
            if (fuel < minFuel) {
                minFuel = fuel;
            }

        }

        return minFuel;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split(",");

        List<Long> inputLong = Arrays.stream(input).map(Long::parseLong).collect(Collectors.toList());

        var max = Arrays.stream(input).mapToInt(Integer::parseInt).max().orElse(0);

        int[] priceTable = new int[max + 1];
        priceTable[0] = 0;
        for (int i = 1; i < priceTable.length; i++) {
            priceTable[i] = priceTable[i-1] + i;
        }

        long minFuel = Long.MAX_VALUE;
        for (int i = 0; i < max + 1; i++) {

            long fuel = 0L;
            for (Long aLong : inputLong) {
                var distance = Math.abs(aLong - i);
                fuel += priceTable[(int) distance];
            }

            log.info("{}: {}", i, fuel);
            if (fuel < minFuel) {
                minFuel = fuel;
            }

        }

        return minFuel;
    }


    @Override
    public int getDayNumber() {
        return 7;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
