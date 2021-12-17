package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day17Test {

    final String testData = "target area: x=20..30, y=-10..-5";

    Day tested = new Day17();

    @Test
    void testPart1_simpleData() {

        assertEquals(45L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(112L, tested.part2(testData));

    }

}