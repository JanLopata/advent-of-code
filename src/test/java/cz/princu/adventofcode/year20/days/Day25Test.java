package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day25Test {

    Day25 tested = new Day25() ;

    @Test
    void testPart1_simpleData() {

        String testData = "5764801\n17807724";

        Assertions.assertEquals(14897079L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}