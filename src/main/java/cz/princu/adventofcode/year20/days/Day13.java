package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day13 extends Day {
    public static void main(String[] args) throws IOException {
        new Day13().printParts();
    }

    @Override
    public Object part1(String data) {


        String[] input = data.split("\n");

        int minimalTime = Integer.parseInt(input[0]);
        String busLinesInput = input[1];

        final Set<Integer> busLinesSet = Arrays.stream(busLinesInput.split(",")).filter(it -> !"x".equals(it)).map(Integer::parseInt).collect(Collectors.toSet());

        BusDeparture earliestBusDeparture = getEarliestBusDeparture(busLinesSet, minimalTime);

        return earliestBusDeparture.getLine() * (earliestBusDeparture.getTime() - minimalTime);

    }

    private BusDeparture getEarliestBusDeparture(Set<Integer> busLinesSet, int minimalTime) {

        long time = minimalTime;
        while (true) {

            for (Integer lineNumber : busLinesSet) {

                if (time % lineNumber == 0) {
                    return new BusDeparture(lineNumber, time);
                }

            }

            time++;
        }

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");
        String busLinesInput = input[1];

        final List<Integer> requiredBuses = Arrays.stream(busLinesInput.split(",")).map(it -> "x".equals(it) ? -1 : Integer.parseInt(it)).collect(Collectors.toList());

        final int[] requirementIndices = getRequirementIndices(requiredBuses);
        final int[] requirementBusNumbers = new int[requirementIndices.length];
        for (int i = 0; i < requirementIndices.length; i++) {
            requirementBusNumbers[i] = requiredBuses.get(requirementIndices[i]);
        }

        long result = 0;

        for (int i = 0; i < requirementBusNumbers.length; i++) {

            final int base = requirementBusNumbers[i];
            long megatron = getMegatron(requirementBusNumbers, base);

            final int remainder = (base - requirementIndices[i]) % base;
            final long inverse = getInverseMod(megatron, base);
            result += remainder * megatron * inverse;

        }

        return result % getMegatron(requirementBusNumbers, -1);

    }

    private int[] getRequirementIndices(List<Integer> requiredBuses) {
        final int requirementsCount = (int) requiredBuses.stream().filter(it -> it > 0).count();

        int[] indices = new int[requirementsCount];

        int idx = 0;
        for (int i = 0; i < requiredBuses.size(); i++) {
            final Integer busNumber = requiredBuses.get(i);
            if (busNumber > 0) {
                indices[idx] = i;
                idx++;
            }
        }
        return indices;
    }


    private long getMegatron(int[] numbers, int ignoredNumber) {

        long result = 1;
        for (int number : numbers) {
            if (number != ignoredNumber)
                result *= number;
        }
        return result;

    }

    private long getInverseMod(long number, int base) {

        for (int x = 0; x < base; x++) {
            if ((number * x) % base == 1)
                return x;
        }

        return -1;
    }


    @AllArgsConstructor
    @Getter
    private static class BusDeparture {

        final int line;
        final long time;

    }


    @Override
    public int getDayNumber() {
        return 13;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
