package adventofcode.year20.days;

import cz.princu.adventofcode.year20.days.Day1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day1Test {

    Day1 tested = new Day1();

    @Test
    void testPart1_simpleData() {

        String data = "1721\n979\n366\n299\n675\n1456";

        Assertions.assertEquals(514579L, tested.part1(data));


    }

    @Test
    void testPart2_simpleData() {
        String data = "1721\n979\n366\n299\n675\n1456";

        Assertions.assertEquals(241861950L, tested.part2(data));

    }


}