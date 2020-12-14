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

        String testData = "mask = 000000000000000000000000000000X1001X\n" +
                "mem[42] = 100\n" +
                "mask = 00000000000000000000000000000000X0XX\n" +
                "mem[26] = 1";

        Assertions.assertEquals(208L, tested.part2(testData));

    }

}