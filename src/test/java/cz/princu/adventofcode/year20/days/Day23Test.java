package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day23Test {

    Day23 tested = new Day23();

    @Test
    void testPart1_simpleData() {


        tested.setPart1Rounds(10);

        String testData = "389125467";

        Assertions.assertEquals("92658374", tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData = "389125467";

        Assertions.assertEquals(149245887792L, tested.part2(testData));

    }

}