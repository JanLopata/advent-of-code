package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day22Test {

    final String testData = "on x=-20..26,y=-36..17,z=-47..7\n" +
            "on x=-20..33,y=-21..23,z=-26..28\n" +
            "on x=-22..28,y=-29..23,z=-38..16\n" +
            "on x=-46..7,y=-6..46,z=-50..-1\n" +
            "on x=-49..1,y=-3..46,z=-24..28\n" +
            "on x=2..47,y=-22..22,z=-23..27\n" +
            "on x=-27..23,y=-28..26,z=-21..29\n" +
            "on x=-39..5,y=-6..47,z=-3..44\n" +
            "on x=-30..21,y=-8..43,z=-13..34\n" +
            "on x=-22..26,y=-27..20,z=-29..19\n" +
            "off x=-48..-32,y=26..41,z=-47..-37\n" +
            "on x=-12..35,y=6..50,z=-50..-2\n" +
            "off x=-48..-32,y=-32..-16,z=-15..-5\n" +
            "on x=-18..26,y=-33..15,z=-7..46\n" +
            "off x=-40..-22,y=-38..-28,z=23..41\n" +
            "on x=-16..35,y=-41..10,z=-47..6\n" +
            "off x=-32..-23,y=11..30,z=-14..3\n" +
            "on x=-49..-5,y=-3..45,z=-29..18\n" +
            "off x=18..30,y=-20..-8,z=-3..13\n" +
            "on x=-41..9,y=-7..43,z=-33..15";

    final String testDataWithLarge = "on x=-20..26,y=-36..17,z=-47..7\n" +
            "on x=-20..33,y=-21..23,z=-26..28\n" +
            "on x=-22..28,y=-29..23,z=-38..16\n" +
            "on x=-46..7,y=-6..46,z=-50..-1\n" +
            "on x=-49..1,y=-3..46,z=-24..28\n" +
            "on x=2..47,y=-22..22,z=-23..27\n" +
            "on x=-27..23,y=-28..26,z=-21..29\n" +
            "on x=-39..5,y=-6..47,z=-3..44\n" +
            "on x=-30..21,y=-8..43,z=-13..34\n" +
            "on x=-22..26,y=-27..20,z=-29..19\n" +
            "off x=-48..-32,y=26..41,z=-47..-37\n" +
            "on x=-12..35,y=6..50,z=-50..-2\n" +
            "off x=-48..-32,y=-32..-16,z=-15..-5\n" +
            "on x=-18..26,y=-33..15,z=-7..46\n" +
            "off x=-40..-22,y=-38..-28,z=23..41\n" +
            "on x=-16..35,y=-41..10,z=-47..6\n" +
            "off x=-32..-23,y=11..30,z=-14..3\n" +
            "on x=-49..-5,y=-3..45,z=-29..18\n" +
            "off x=18..30,y=-20..-8,z=-3..13\n" +
            "on x=-41..9,y=-7..43,z=-33..15\n" +
            "on x=-54112..-39298,y=-85059..-49293,z=-27449..7877\n" +
            "on x=967..23432,y=45373..81175,z=27513..53682";

    final String smallData = "on x=10..12,y=10..12,z=10..12\n" +
            "on x=11..13,y=11..13,z=11..13\n" +
            "off x=9..11,y=9..11,z=9..11\n" +
            "on x=10..10,y=10..10,z=10..10";

    final String twoPointData = "on x=0..2,y=0..2,z=0..2\n" +
            "on x=1..3,y=1..3,z=1..3";

    final String twoDimData = "on x=0..2,y=0..2,z=0..0\n" +
            "on x=1..3,y=1..3,z=0..0";

    final String threePointData = "on x=10..12,y=10..12,z=10..12\n" +
            "on x=11..13,y=11..13,z=11..13\n" +
            "off x=9..11,y=9..11,z=9..11";


    Day tested = new Day22();

    @Test
    void testPart1_smallData() {

        Assertions.assertEquals(39L, tested.part1(smallData));

    }

    @Test
    void testPart1_twoPointData() {

        Assertions.assertEquals(46L, tested.part1(twoPointData));

    }

    @Test
    void testPart1_threePointData() {

        Assertions.assertEquals(38L, tested.part1(threePointData));

    }
    @Test
    void testPart1_twoDimData() {

        Assertions.assertEquals(14L, tested.part1(twoDimData));

    }


    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(590784L, tested.part1(testData));

    }

    @Test
    void testPart1_largeData() {

        Assertions.assertEquals(2758514936282235L, tested.part1(testDataWithLarge));

    }

    @Test
    void testPart2_simpleData() {

        Assertions.assertEquals(0L, tested.part2(testData));

    }

}