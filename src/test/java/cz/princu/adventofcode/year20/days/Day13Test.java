package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day13Test {

    Day13 tested = new Day13();

    @Test
    void testPart1_simpleData() {

        String testData = "939\n" +
                "7,13,x,x,59,x,31,19";

        Assertions.assertEquals(295L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "939\n" +
                "7,13,x,x,59,x,31,19";

        Assertions.assertEquals(1068781L, tested.part2(testData));

    }


}