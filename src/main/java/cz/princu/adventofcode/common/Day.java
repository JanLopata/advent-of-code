package cz.princu.adventofcode.common;

import java.io.IOException;

public interface Day extends DoesFileOperations {
    Object part1(String data);

    Object part2(String data);

    int getDayNumber();
    int getYear();

    default void printParts() throws IOException {

        System.out.println("Part 1: " + part1(getDayData(getDayNumber(), getYear())));
        System.out.println("Part 2: " + part2(getDayData(getDayNumber(), getYear())));
    }
}
