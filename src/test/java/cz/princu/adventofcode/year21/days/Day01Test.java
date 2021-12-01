package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day01Test {

    Day01 tested = new Day01();

    @Test
    void testPart1_simpleData() {

        String data = "199\n" +
                "200\n" +
                "208\n" +
                "210\n" +
                "200\n" +
                "207\n" +
                "240\n" +
                "269\n" +
                "260\n" +
                "263";

        Assertions.assertEquals(7L, tested.part1(data));


    }

    @Test
    void testPart2_simpleData() {
        String data = "199\n" +
                "200\n" +
                "208\n" +
                "210\n" +
                "200\n" +
                "207\n" +
                "240\n" +
                "269\n" +
                "260\n" +
                "263";

        Assertions.assertEquals(5L, tested.part2(data));

    }


}