package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day09Test {

    Day09 tested = new Day09();

    @Test
    void testPart1_simpleData() {

        String testData =
                "35\n" +
                        "20\n" +
                        "15\n" +
                        "25\n" +
                        "47\n" +
                        "40\n" +
                        "62\n" +
                        "55\n" +
                        "65\n" +
                        "95\n" +
                        "102\n" +
                        "117\n" +
                        "150\n" +
                        "182\n" +
                        "127\n" +
                        "219\n" +
                        "299\n" +
                        "277\n" +
                        "309\n" +
                        "576";

        tested.setPreambleLength(5);
        Assertions.assertEquals(127L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData =
                "35\n" +
                        "20\n" +
                        "15\n" +
                        "25\n" +
                        "47\n" +
                        "40\n" +
                        "62\n" +
                        "55\n" +
                        "65\n" +
                        "95\n" +
                        "102\n" +
                        "117\n" +
                        "150\n" +
                        "182\n" +
                        "127\n" +
                        "219\n" +
                        "299\n" +
                        "277\n" +
                        "309\n" +
                        "576";

        tested.setPreambleLength(5);
        Assertions.assertEquals(62L, tested.part2(testData));

    }


}