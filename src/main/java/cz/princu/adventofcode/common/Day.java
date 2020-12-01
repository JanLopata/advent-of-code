package cz.princu.adventofcode.common;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public abstract class Day implements DoesFileOperations {

    public abstract Object part1(String data);

    public abstract Object part2(String data);

    public abstract int getDayNumber();

    public abstract int getYear();

    public void printParts() throws IOException {

        log.info("Part 1: {}", part1(getDayData(getDayNumber(), getYear())));
        log.info("Part 2: {}", part2(getDayData(getDayNumber(), getYear())));
    }
}
