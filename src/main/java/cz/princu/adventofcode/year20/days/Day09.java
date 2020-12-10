package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

public class Day09 extends Day {

    public static void main(String[] args) throws IOException {
        new Day09().printParts();
    }

    private static Integer PREAMBLE_LENGTH = 25;

    public void setPreambleLength(int preambleLength) {
        PREAMBLE_LENGTH = preambleLength;
    }

    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");

        return getFirstErrorNumber(lines);


    }

    private Long getFirstErrorNumber(String[] lines) {

        long[] linesInt = Arrays.stream(lines).mapToLong(Long::parseLong).toArray();

        for (int currentPos = PREAMBLE_LENGTH; currentPos < linesInt.length; currentPos++) {
            if (!checkPreambleSumsUp(linesInt, currentPos))
                return linesInt[currentPos];
        }


        return 0L;
    }

    private boolean checkPreambleSumsUp(long[] linesInt, int current) {


        for (int i = current - PREAMBLE_LENGTH; i < current; i++) {

            for (int j = current - PREAMBLE_LENGTH; j < current; j++) {
                if (i == j)
                    break;

                if (linesInt[i] + linesInt[j] == linesInt[current])
                    return true;
            }

        }

        return false;
    }




    @Override
    public Object part2(String data) {
        String[] lines = data.split("\n");

        Long target = (Long) part1(data);


        return findAnswer(lines, target);


    }

    private Long findAnswer(String[] lines, Long target) {


        long[] linesInt = Arrays.stream(lines).mapToLong(Long::parseLong).toArray();


        for (int i = 0; i < linesInt.length; i++) {
            for (int j = 0; j < linesInt.length; j++) {

                if (i >= j)
                    continue;

                long sum = sumUp(linesInt, i, j);
                if (sum == target) {

                     return minUp(linesInt, i, j) + maxUp(linesInt, i, j);
                }


            }
        }
        return 0L;

    }

    private long maxUp(long[] linesInt, int i, int j) {

        long max = Long.MIN_VALUE;
        for (int k = i; k < j; k++) {
            if (linesInt[k] > max)
                max = linesInt[k];
        }

        return max;
    }

    private long minUp(long[] linesInt, int i, int j) {

        long min = Long.MAX_VALUE;
        for (int k = i; k < j; k++) {
            if (linesInt[k] < min)
                min = linesInt[k];
        }

        return min;
    }

    private long sumUp(long[] linesInt, int i, int j) {

        long sum = 0;
        for (int k = i; k < j; k++) {
            sum+= linesInt[k];
        }

        return sum;
    }

    @Override
    public int getDayNumber() {
        return 9;
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Builder
    @Getter
    @ToString
    static class BagRule {
        private final String outerBag;
        private final Map<String, Integer> innerBagsMap;
    }

}
