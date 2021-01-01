package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day21Test {

    Day21 tested = new Day21();

    @Test
    void testPart1_simpleData() {

        String testData = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
                "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
                "sqjhc fvjkl (contains soy)\n" +
                "sqjhc mxmxvkd sbzzf (contains fish)";

        Assertions.assertEquals(5L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "mxmxvkd kfcds sqjhc nhms (contains dairy, fish)\n" +
                "trh fvjkl sbzzf mxmxvkd (contains dairy)\n" +
                "sqjhc fvjkl (contains soy)\n" +
                "sqjhc mxmxvkd sbzzf (contains fish)";

        Assertions.assertEquals("mxmxvkd,sqjhc,fvjkl", tested.part2(testData));

    }

}