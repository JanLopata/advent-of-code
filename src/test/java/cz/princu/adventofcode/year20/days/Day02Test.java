package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day02Test {

    Day02 tested = new Day02();

    @Test
    void testPart1_simpleData() {

        String testData = "1-3 a: abcde\n" +
                "1-3 b: cdefg\n" +
                "2-9 c: ccccccccc";

        Assertions.assertEquals(2L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData = "1-3 a: abcde\n" +
                "1-3 b: cdefg\n" +
                "2-9 c: ccccccccc";

        Assertions.assertEquals(1L, tested.part2(testData));

    }




}