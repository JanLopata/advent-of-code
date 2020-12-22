package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day22Test {

    Day22 tested = new Day22();

    @Test
    void testPart1_simpleData() {

        String testData = "Player 1:\n" +
                "9\n" +
                "2\n" +
                "6\n" +
                "3\n" +
                "1\n" +
                "\n" +
                "Player 2:\n" +
                "5\n" +
                "8\n" +
                "4\n" +
                "7\n" +
                "10";

        Assertions.assertEquals(306L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "Player 1:\n" +
                "9\n" +
                "2\n" +
                "6\n" +
                "3\n" +
                "1\n" +
                "\n" +
                "Player 2:\n" +
                "5\n" +
                "8\n" +
                "4\n" +
                "7\n" +
                "10";

        Assertions.assertEquals(291L, tested.part2(testData));

    }

}