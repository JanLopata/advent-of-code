package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day19Test {

    Day19 tested = new Day19();

    @Test
    void testPart1_simpleData() {

        String testData = "0: 4 1 5\n" +
                "1: 2 3 | 3 2\n" +
                "2: 4 4 | 5 5\n" +
                "3: 4 5 | 5 4\n" +
                "4: \"a\"\n" +
                "5: \"b\"\n" +
                "\n" +
                "ababbb\n" +
                "bababa\n" +
                "abbbab\n" +
                "aaabbb\n" +
                "aaaabbb";

        Assertions.assertEquals(2L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}