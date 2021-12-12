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
        int[] priceTable = getSimplePriceTable(max);
        return findMinimumFuelConsumption(inputLong, max, priceTable);
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split(",");
        List<Long> inputLong = Arrays.stream(input).map(Long::parseLong).collect(Collectors.toList());
        var max = Arrays.stream(input).mapToInt(Integer::parseInt).max().orElse(0);
        int[] priceTable = getRaisingPriceTable(max);
        return findMinimumFuelConsumption(inputLong, max, priceTable);
    }

    private int[] getRaisingPriceTable(int max) {
        int[] priceTable = new int[max + 1];
        priceTable[0] = 0;
        for (int i = 1; i < priceTable.length; i++) {
            priceTable[i] = priceTable[i-1] + i;
        }
        return priceTable;
    }


    private int[] getSimplePriceTable(int max) {
        int[] priceTable = new int[max + 1];
        priceTable[0] = 0;
        for (int i = 1; i < priceTable.length; i++) {
            priceTable[i] = i;
        }
        return priceTable;
    }


    private long findMinimumFuelConsumption(List<Long> inputLong, int max, int[] priceTable) {
        long minFuel = Long.MAX_VALUE;
        for (int i = 0; i < max + 1; i++) {

            long fuel = 0L;
            for (Long aLong : inputLong) {
                var distance = Math.abs(aLong - i);
                fuel += priceTable[(int) distance];
            }

            log.debug("{}: {}", i, fuel);
            if (fuel < minFuel) {
                minFuel = fuel;
            } else {
                // fuel consumption is only raising from now
                return minFuel;
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
