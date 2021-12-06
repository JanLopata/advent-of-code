package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
public class Day06 extends Day {
    public static void main(String[] args) throws IOException {
        new Day06().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split(",");

        return computeLanterns(input, 80, 2, 7);
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split(",");

        return computeLanterns(input, 256, 2, 7);

    }

    private long computeLanterns(String[] input, int steps, int daysForNewPlus, int daysToProduce) {
        final int maxState = daysForNewPlus + daysToProduce - 1;


        long[] currentState = initCurrentState(input, maxState);
        long[] newState = new long[maxState + 1];

        for (long i = 0; i < steps; i++) {

            log.info("{}: {}", i, Arrays.stream(currentState).boxed().collect(Collectors.toList()));
            long newCount = newState[0];
            System.arraycopy(currentState, 1, newState, 0, maxState);
            newState[maxState] = newCount;
            newState[daysToProduce - 1] = newState[daysToProduce - 1] + newCount;
            currentState = newState;

        }

        log.info("{}: {}", steps, Arrays.stream(currentState).boxed().collect(Collectors.toList()));

        long sum = 0;
        for (int i = 0; i < maxState + 1; i++) {
            sum += currentState[i];
        }

        return sum;
    }

    private long[] initCurrentState(String[] input, int maxState) {
        long[] currentState = new long[maxState + 1];
        for (int i = 0; i < maxState + 1; i++) {
            currentState[i] = 0;
        }

        for (String s : input) {
            int value = Integer.parseInt(s);
            currentState[value] = currentState[value] + 1;
        }
        return currentState;
    }


    @Override
    public int getDayNumber() {
        return 6;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
