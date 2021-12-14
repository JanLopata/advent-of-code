package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day14Test {

    final String testData = "NNCB\n" +
            "\n" +
            "CH -> B\n" +
            "HH -> N\n" +
            "CB -> H\n" +
            "NH -> C\n" +
            "HB -> C\n" +
            "HC -> B\n" +
            "HN -> C\n" +
            "NN -> C\n" +
            "BH -> H\n" +
            "NC -> B\n" +
            "NB -> B\n" +
            "BN -> B\n" +
            "BB -> N\n" +
            "BC -> B\n" +
            "CC -> N\n" +
            "CN -> C";

    Day tested = new Day14();

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(1588L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(2188189693529L, tested.part2(testData));

    }

}