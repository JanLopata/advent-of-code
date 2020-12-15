package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day15Test {

    Day15 tested = new Day15();

    @Test
    void testPart1_simpleData() {


        Assertions.assertEquals(436L, tested.part1("0,3,6"));
        Assertions.assertEquals(1L, tested.part1("1,3,2"));
        Assertions.assertEquals(10L, tested.part1("2,1,3"));
        Assertions.assertEquals(27L, tested.part1("1,2,3"));
        Assertions.assertEquals(78L, tested.part1("2,3,1"));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}