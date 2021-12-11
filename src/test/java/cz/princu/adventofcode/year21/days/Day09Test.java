package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day09Test {

    Day09 tested = new Day09();
    String testData = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678";

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(15L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(1134L, tested.part2(testData));

    }

}