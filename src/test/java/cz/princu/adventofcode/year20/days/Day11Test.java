package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day11Test {

    Day11 tested = new Day11();

    @Test
    void testPart1_simpleData() {

        String testData =
                "L.LL.LL.LL\n" +
                        "LLLLLLL.LL\n" +
                        "L.L.L..L..\n" +
                        "LLLL.LL.LL\n" +
                        "L.LL.LL.LL\n" +
                        "L.LLLLL.LL\n" +
                        "..L.L.....\n" +
                        "LLLLLLLLLL\n" +
                        "L.LLLLLL.L\n" +
                        "L.LLLLL.LL\n";

        Assertions.assertEquals(37L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

//        Assertions.assertEquals(62L, tested.part2(testData));

    }


}