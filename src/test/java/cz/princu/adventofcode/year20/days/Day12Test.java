package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day12Test {

    Day12 tested = new Day12();

    @Test
    void testPart1_simpleData() {

        String testData =
                "F10\n" +
                        "N3\n" +
                        "F7\n" +
                        "R90\n" +
                        "F11";

        Assertions.assertEquals(25, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData =
                "F10\n" +
                        "N3\n" +
                        "F7\n" +
                        "R90\n" +
                        "F11";

        Assertions.assertEquals(286, tested.part2(testData));

    }


}