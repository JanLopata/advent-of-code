package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Day25 extends Day {
    public static void main(String[] args) throws IOException {
        new Day25().printParts();
    }

    private static final long PART1_BASE = 20201227L;

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        long cardPK = Long.parseLong(input[0]);
        long doorPK = Long.parseLong(input[1]);

//        int cardLoopSize = findLoopSize(cardPK);
        int doorLoopSize = findLoopSize(doorPK);

        return performLoops(cardPK, doorLoopSize, PART1_BASE);

    }

    private int findLoopSize(long publicKey) {


        int loopSize = 0;
        long value = 1;
        long subjectNumber = 7L;

        while (value != publicKey) {
            loopSize++;

            value *= subjectNumber;
            value = value % PART1_BASE;

            if (loopSize % 1000 == 0)
                log.info("{} loop size tested", loopSize);
        }

        return loopSize;
    }

    private Long performLoops(long subjectNumber, int loopSize, long base) {

        long value = 1;
        for (int i = 0; i < loopSize; i++) {
            value *= subjectNumber;
            value = value % base;
        }

        return value;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 25;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
