package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day24Test {

    final String testData = "13579246899999";

    Day tested = new Day24();

    @Test
    void testPart1_simpleData() {

        assertEquals(0L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(testData));

    }

}