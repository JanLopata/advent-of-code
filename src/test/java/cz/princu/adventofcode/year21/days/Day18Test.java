package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

    final String testData =
//            "[1,2]\n" +
            "[[1,2],3]\n" +
            "[9,[8,7]]\n" +
            "[[1,9],[8,5]]\n" +
            "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]\n" +
            "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]\n" +
            "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]";

    Day tested = new Day18();

    @Test
    void testPart1_simpleData() {

        assertEquals(0L, tested.part1(testData));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(testData));

    }

    @Test
    void testExplode() {

        Day18.Term term = new Day18.Term();


    }


}