package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day07Test {

    Day07 tested = new Day07();
    String testData = "16,1,2,0,4,2,7,1,2,14";


    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(37L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        Assertions.assertEquals(168L, tested.part2(testData));

    }

}