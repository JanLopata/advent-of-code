package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day16Test {


    Day tested = new Day16();


    @ParameterizedTest
    @CsvSource({"D2FE28,6", "38006F45291200,9", "EE00D40C823060,14", "8A004A801A8002F478,16",
            "620080001611562C8802118E34,12", "C0015000016115A2E0802F182340,23", "A0016C880162017C3686B18A3D4780,31"})
    void test_part1(String input, String expected) {
        assertEquals(expected, tested.part1(input).toString());

    }

    @ParameterizedTest
    @CsvSource({"C200B40A82,3", "04005AC33890,54", "880086C3E88112,7", "CE00C43D881120,9",
            "D8005AC2A8F0,1", "F600BC2D8F,0", "9C005AC2F8F0,0", "9C0141080250320F1802104A08, 1"})
    void testPart2_simpleData(String input, String expected) {
        assertEquals(expected, tested.part2(input).toString());

    }

}