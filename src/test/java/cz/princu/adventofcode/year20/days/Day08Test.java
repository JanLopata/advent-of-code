package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day08Test {

    Day08 tested = new Day08();

    @Test
    void testPart1_simpleData() {

        String testData =
                "nop +0\n" +
                        "acc +1\n" +
                        "jmp +4\n" +
                        "acc +3\n" +
                        "jmp -3\n" +
                        "acc -99\n" +
                        "acc +1\n" +
                        "jmp -4\n" +
                        "acc +6";

        Assertions.assertEquals(5L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {
        String testData =
                "nop +0\n" +
                        "acc +1\n" +
                        "jmp +4\n" +
                        "acc +3\n" +
                        "jmp -3\n" +
                        "acc -99\n" +
                        "acc +1\n" +
                        "jmp -4\n" +
                        "acc +6";

        Assertions.assertEquals(8L, tested.part2(testData));

    }


}