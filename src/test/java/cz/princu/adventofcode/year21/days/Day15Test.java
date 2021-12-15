package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day15Test {

    final String testData = "1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581";

    Day tested = new Day15();

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(40L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(2188189693529L, tested.part2(testData));

    }

}