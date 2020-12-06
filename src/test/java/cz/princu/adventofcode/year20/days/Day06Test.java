package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day06Test {

    Day06 tested = new Day06();

    @Test
    void testPart1_simpleData() {

        String testData =
                "abc\n" +
                        "\n" +
                        "a\n" +
                        "b\n" +
                        "c\n" +
                        "\n" +
                        "ab\n" +
                        "ac\n" +
                        "\n" +
                        "a\n" +
                        "a\n" +
                        "a\n" +
                        "a\n" +
                        "\n" +
                        "b";

        Assertions.assertEquals(11L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData =
                "abc\n" +
                        "\n" +
                        "a\n" +
                        "b\n" +
                        "c\n" +
                        "\n" +
                        "ab\n" +
                        "ac\n" +
                        "\n" +
                        "a\n" +
                        "a\n" +
                        "a\n" +
                        "a\n" +
                        "\n" +
                        "b";

        Assertions.assertEquals(6L, tested.part2(testData));

    }

}