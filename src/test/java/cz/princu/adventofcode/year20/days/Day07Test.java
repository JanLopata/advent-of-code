package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day07Test {

    Day07 tested = new Day07();

    @Test
    void testPart1_simpleData() {

        String testData =
                "light red bags contain 1 bright white bag, 2 muted yellow bags.\n" +
                        "dark orange bags contain 3 bright white bags, 4 muted yellow bags.\n" +
                        "bright white bags contain 1 shiny gold bag.\n" +
                        "muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.\n" +
                        "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.\n" +
                        "dark olive bags contain 3 faded blue bags, 4 dotted black bags.\n" +
                        "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.\n" +
                        "faded blue bags contain no other bags.\n" +
                        "dotted black bags contain no other bags.";

        Assertions.assertEquals(4L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

    }

}