package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day06Test {

    Day06 tested = new Day06();

    String testData = "3,4,3,1,2";

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(5934L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(26984457539L, tested.part2(testData));

    }

}