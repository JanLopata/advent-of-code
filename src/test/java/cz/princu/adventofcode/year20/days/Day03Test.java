package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day03Test {

    Day03 tested = new Day03();

    @Test
    void testPart1_simpleData() {

        String testData =
                "..##.......\n" +
                        "#...#...#..\n" +
                        ".#....#..#.\n" +
                        "..#.#...#.#\n" +
                        ".#...##..#.\n" +
                        "..#.##.....\n" +
                        ".#.#.#....#\n" +
                        ".#........#\n" +
                        "#.##...#...\n" +
                        "#...##....#\n" +
                        ".#..#...#.#";

        Assertions.assertEquals(7, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        String testData =
                "..##.......\n" +
                        "#...#...#..\n" +
                        ".#....#..#.\n" +
                        "..#.#...#.#\n" +
                        ".#...##..#.\n" +
                        "..#.##.....\n" +
                        ".#.#.#....#\n" +
                        ".#........#\n" +
                        "#.##...#...\n" +
                        "#...##....#\n" +
                        ".#..#...#.#";

        Assertions.assertEquals(336L, tested.part2(testData));

    }

}