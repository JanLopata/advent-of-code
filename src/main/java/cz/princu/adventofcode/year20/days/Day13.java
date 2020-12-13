package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
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

        log.info("{}", requiredBuses);
        final long minimalStep = getMinimalStep(requiredBuses);
        log.info("minimal step {}", minimalStep);

        final int[] requirementIndices = getRequirementIndices(requiredBuses);
        final int [] requirementBusNumbers = new int[requirementIndices.length];
        for (int i = 0; i < requirementIndices.length; i++) {
            requirementBusNumbers[i] = requiredBuses.get(requirementIndices[i]);
        }

        long time = 0;
        while (true) {

            int success = 0;

            for (int i = 0; i < requirementBusNumbers.length; i++) {

                if (time % requirementBusNumbers[i] == requirementIndices[i])
                    success++;

            }

            if (success == requirementBusNumbers.length)
                return time;

            if (success > requirementBusNumbers.length - 1) {
                log.info("time {} success {}", time, success);
            }

            time += minimalStep;

        }

    }

    private int[] getRequirementIndices(List<Integer> requiredBuses) {
        final int requirementsCount = (int) requiredBuses.stream().filter(it -> it > 0).count();


        int [] indices = new int[requirementsCount];

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


    private Long getMinimalStep(List<Integer> requiredBuses) {

//        Set<Integer> requirementIndices = new HashSet<>();
//        for (int i = 0; i < requiredBuses.size(); i++) {
//            if (requiredBuses.get(i) != -1)
//                requirementIndices.add(i);
//        }
//
//        long minimalStep = 1;
//        for (Integer index : requirementIndices) {
//
//            for (Integer requiredBus : requiredBuses) {
//                if (index.equals(requiredBus)) {
//                    final int candidate = index * requiredBuses.get(index);
//                    if (candidate > minimalStep)
//                        minimalStep = candidate;
//
//                }
//            }
//        }
//
//        return minimalStep;


        return requiredBuses.get(0).longValue();
    }

    private Long nextKIntersect(int p1, int p2, int diff) {

        for (long n = 0; n < p1 * p2 * 10; n++) {

            if (n % p1 == 0 && n % p2 == diff) {
                System.out.println(n);
            }


        }

        return 0L;

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
