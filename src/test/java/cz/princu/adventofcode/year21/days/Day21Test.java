package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day21Test {

    final String testData = "Player 1 starting position: 4\n" +
            "Player 2 starting position: 8";

    Day tested = new Day21();

    @Test
    void testPart1_simpleData() {

        assertEquals(739785L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(testData));

    }

}