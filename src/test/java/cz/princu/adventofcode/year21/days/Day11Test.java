package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day11Test {

    Day11 tested = new Day11();
    String testData = "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526";

    @Test
    void testPart1_simpleData() {

        assertEquals(1656L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        assertEquals(195L, tested.part2(testData));

    }

}