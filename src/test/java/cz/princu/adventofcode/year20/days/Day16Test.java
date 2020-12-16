package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day16Test {

    Day16 tested = new Day16();

    @Test
    void testPart1_simpleData() {

        String testData = "class: 1-3 or 5-7\n" +
                "row: 6-11 or 33-44\n" +
                "seat: 13-40 or 45-50\n" +
                "\n" +
                "your ticket:\n" +
                "7,1,14\n" +
                "\n" +
                "nearby tickets:\n" +
                "7,3,47\n" +
                "40,4,50\n" +
                "55,2,20\n" +
                "38,6,12";

        Assertions.assertEquals(71L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}