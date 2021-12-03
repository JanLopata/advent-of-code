package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day02Test {

    Day02 tested = new Day02();

    @Test
    void testPart1_simpleData() {

        String testData = "forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2";

        Assertions.assertEquals(150L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "forward 5\n" +
                "down 5\n" +
                "forward 8\n" +
                "up 3\n" +
                "down 8\n" +
                "forward 2";

        Assertions.assertEquals(900L, tested.part2(testData));

    }

}