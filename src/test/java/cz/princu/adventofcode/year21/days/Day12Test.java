package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day12Test {

    Day12 tested = new Day12();
    String smallData = "start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end";

    String advancedData = "dc-end\n" +
            "HN-start\n" +
            "start-kj\n" +
            "dc-start\n" +
            "dc-HN\n" +
            "LN-dc\n" +
            "HN-end\n" +
            "kj-sa\n" +
            "kj-HN\n" +
            "kj-dc";


    @Test
    void testPart1_simpleData() {

        assertEquals(10L, tested.part1(smallData));

    }

    @Test
    void testPart1_advancedData() {

        assertEquals(19L, tested.part1(advancedData));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(36L, tested.part2(smallData));

    }


    @Test
    void testPart2_advancedData() {

        assertEquals(103L, tested.part2(advancedData));

    }

}