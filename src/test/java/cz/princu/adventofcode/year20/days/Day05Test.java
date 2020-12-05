package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day05Test {

    Day05 tested = new Day05();

    @Test
    void testPart1_simpleData() {

        String testData = "BFFFBBFRRR\n" +
                "FFFBBBFRRR\n" +
                "BBFFBBFRLL";

        Assertions.assertEquals(820L, tested.part1(testData));

    }

}