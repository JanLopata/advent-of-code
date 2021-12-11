package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day05Test {

    Day05 tested = new Day05();

    String testData = "0,9 -> 5,9\n" +
            "8,0 -> 0,8\n" +
            "9,4 -> 3,4\n" +
            "2,2 -> 2,1\n" +
            "7,0 -> 7,4\n" +
            "6,4 -> 2,0\n" +
            "0,9 -> 2,9\n" +
            "3,4 -> 1,4\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2";

    String smallData = "8,0 -> 0,8\n" +
            "6,4 -> 2,0\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2";

    @Test
    void testPart1_simpleData() {


        Assertions.assertEquals(5L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(12L, tested.part2(testData));

    }

}