package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {

    final String testData1 = "8A004A801A8002F478";


    Day tested = new Day16();

    @Test
    void testPart1_simpleData() {

//        assertEquals(6L, tested.part1("D2FE28"));
//        assertEquals(9L, tested.part1("38006F45291200"));
//        assertEquals(14L, tested.part1("EE00D40C823060"));
//
//        assertEquals(16L, tested.part1("8A004A801A8002F478"));
//        assertEquals(12L, tested.part1("620080001611562C8802118E34"));
        assertEquals(23L, tested.part1("C0015000016115A2E0802F182340"));
//        assertEquals(31L, tested.part1("A0016C880162017C3686B18A3D4780"));

    }

    @ParameterizedTest
    @CsvSource({"D2FE28,6", "38006F45291200,9", "EE00D40C823060,14", "8A004A801A8002F478,16",
            "620080001611562C8802118E34,12", "C0015000016115A2E0802F182340,23", "A0016C880162017C3686B18A3D4780,31"})
    void test_part1(String input, String expected) {
        assertEquals(expected, tested.part1(input).toString());

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(testData1));

    }

}