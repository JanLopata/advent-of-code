package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day10Test {

    Day10 tested = new Day10();

    @Test
    void testPart1_one_simpleData() {

        String testData =
                "16\n" +
                        "10\n" +
                        "15\n" +
                        "5\n" +
                        "1\n" +
                        "11\n" +
                        "7\n" +
                        "19\n" +
                        "6\n" +
                        "12\n" +
                        "4";

        Assertions.assertEquals(35L, tested.part1(testData));

    }

    @Test
    void testPart1_second_simpleData() {

        String testData =
                "28\n" +
                        "33\n" +
                        "18\n" +
                        "42\n" +
                        "31\n" +
                        "14\n" +
                        "46\n" +
                        "20\n" +
                        "48\n" +
                        "47\n" +
                        "24\n" +
                        "23\n" +
                        "49\n" +
                        "45\n" +
                        "19\n" +
                        "38\n" +
                        "39\n" +
                        "11\n" +
                        "1\n" +
                        "32\n" +
                        "25\n" +
                        "35\n" +
                        "8\n" +
                        "17\n" +
                        "7\n" +
                        "9\n" +
                        "4\n" +
                        "2\n" +
                        "34\n" +
                        "10\n" +
                        "3";

        Assertions.assertEquals(220L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData =
                "16\n" +
                        "10\n" +
                        "15\n" +
                        "5\n" +
                        "1\n" +
                        "11\n" +
                        "7\n" +
                        "19\n" +
                        "6\n" +
                        "12\n" +
                        "4";

        Assertions.assertEquals(8L, tested.part2(testData));

    }

    @Test
    void testPart2_second_simpleData() {

        String testData =
                "28\n" +
                        "33\n" +
                        "18\n" +
                        "42\n" +
                        "31\n" +
                        "14\n" +
                        "46\n" +
                        "20\n" +
                        "48\n" +
                        "47\n" +
                        "24\n" +
                        "23\n" +
                        "49\n" +
                        "45\n" +
                        "19\n" +
                        "38\n" +
                        "39\n" +
                        "11\n" +
                        "1\n" +
                        "32\n" +
                        "25\n" +
                        "35\n" +
                        "8\n" +
                        "17\n" +
                        "7\n" +
                        "9\n" +
                        "4\n" +
                        "2\n" +
                        "34\n" +
                        "10\n" +
                        "3";

        Assertions.assertEquals(19208L, tested.part2(testData));

    }

//    @Test
//    void generateValid() {
//
//        tested.generateValid(new long[]{0, 1, 2, 3, 5});
//
//    }



}