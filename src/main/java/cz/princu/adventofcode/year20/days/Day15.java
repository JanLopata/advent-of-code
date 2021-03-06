package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Day15 extends Day {
    public static void main(String[] args) throws IOException {
        new Day15().printParts();
    }

    @Override
    public Object part1(String data) {

        return computeResult(data, 2020L);

    }

    @Override
    public Object part2(String data) {

        return computeResult(data, 30_000_000L);
    }

    private long computeResult(String data, Long target) {
        String[] input = data.split(",");

        Map<Long, Long> lastSpokenMap = new HashMap<>();

        for (int i = 0; i < input.length - 1; i++) {
            lastSpokenMap.put(Long.parseLong(input[i]), (long) i);
        }

        long currentNumber = Long.parseLong(input[input.length - 1]);
        for (int i = input.length - 1; i < target - 1; i++) {

            if (i % (target / 100) == 0) {
                log.info("Progress {}%", i / (target/100));
            }

            if (!lastSpokenMap.containsKey(currentNumber)) {
                lastSpokenMap.put(currentNumber, (long) i);
                currentNumber = 0;
            } else {

                long nextNumber = i - lastSpokenMap.get(currentNumber);
                lastSpokenMap.put(currentNumber, (long) i);
                currentNumber = nextNumber;

            }

        }
        return currentNumber;
    }


    @Override
    public int getDayNumber() {
        return 15;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
