package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day22Test {

    final String testData = "";

    final String smallData = "on x=10..12,y=10..12,z=10..12\n" +
            "on x=11..13,y=11..13,z=11..13\n" +
            "off x=9..11,y=9..11,z=9..11\n" +
            "on x=10..10,y=10..10,z=10..10";
    Day tested = new Day22();

    @Test
    void testPart1_smallData() {

        Assertions.assertEquals(39L, tested.part1(smallData));

    }

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(0L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}