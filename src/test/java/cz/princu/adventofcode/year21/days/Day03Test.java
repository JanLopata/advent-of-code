package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day03Test {

    Day03 tested = new Day03();

    @Test
    void testPart1_simpleData() {

        String testData = "00100\n" +
                "11110\n" +
                "10110\n" +
                "10111\n" +
                "10101\n" +
                "01111\n" +
                "00111\n" +
                "11100\n" +
                "10000\n" +
                "11001\n" +
                "00010\n" +
                "01010";

        Assertions.assertEquals(198L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "00100\n" +
                "11110\n" +
                "10110\n" +
                "10111\n" +
                "10101\n" +
                "01111\n" +
                "00111\n" +
                "11100\n" +
                "10000\n" +
                "11001\n" +
                "00010\n" +
                "01010";

        Assertions.assertEquals(230L, tested.part2(testData));

    }

}