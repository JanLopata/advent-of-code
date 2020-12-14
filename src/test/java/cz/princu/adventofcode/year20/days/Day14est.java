package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day14est {

    Day14 tested = new Day14();

    @Test
    void testPart1_simpleData() {

        String testData = "mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X\n" +
                "mem[8] = 11\n" +
                "mem[7] = 101\n" +
                "mem[8] = 0";

        Assertions.assertEquals(165L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}