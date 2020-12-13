package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day13Test {

    Day13 tested = new Day13();

    @Test
    void testPart1_simpleData() {

        String testData = "939\n" +
                "7,13,x,x,59,x,31,19";

        Assertions.assertEquals(295L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "939\n" +
                "7,13,x,x,59,x,31,19";

        Assertions.assertEquals(1068781L, tested.part2(testData));

    }

    @Test
    void testPart2_two_simpleData() {
        String testData = "\n" + "67,7,59,61";

        Assertions.assertEquals(754018L, tested.part2(testData));

    }

    @Test
    void testPart2_three_simpleData() {
        String testData = "\n" + "67,x,7,59,61";

        Assertions.assertEquals(779210L, tested.part2(testData));

    }

    @Test
    void testPart2_four_simpleData() {
        String testData = "\n" + "67,7,x,59,61";

        Assertions.assertEquals(1261476L, tested.part2(testData));

    }

    @Test
    void testPart2_five_simpleData() {
        String testData = "\n" + "1789,37,47,1889";

        Assertions.assertEquals(1202161486L, tested.part2(testData));

    }

}