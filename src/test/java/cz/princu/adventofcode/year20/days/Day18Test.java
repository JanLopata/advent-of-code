package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day18Test {

    Day18 tested = new Day18();

    @Test
    void testPart1_simpleData() {

        String testData =
                "    2 * 3 + (4 * 5)\n" +
                "    5 + (8 * 3 + 9 + 3 * 4 * 3)\n" +
                "    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))\n" +
                "    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";

        Assertions.assertEquals(26335L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData =
                "1 + (2 * 3) + (4 * (5 + 6))\n" +
                "    2 * 3 + (4 * 5)\n" +
                        "    5 + (8 * 3 + 9 + 3 * 4 * 3)\n" +
                        "    5 * 9 * (7 * 3 * 3 + 9 * 3 + (8 + 6 * 4))\n" +
                        "    ((2 + 4 * 9) * (6 + 9 * 8 + 6) + 6) + 2 + 4 * 2";

        Assertions.assertEquals(693942L, tested.part2(testData));

    }


}