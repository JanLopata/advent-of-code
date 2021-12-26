package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day25Test {

    final String testData = "v...>>.vv>\n" +
            ".vv>>.vv..\n" +
            ">>.>v>...v\n" +
            ">>v>>.>.v.\n" +
            "v>v.vv.v..\n" +
            ">.>>..v...\n" +
            ".vv..>.>v.\n" +
            "v.v..>>v.v\n" +
            "....v..v.>";

    Day25 tested = new Day25();

    @Test
    void testPart1_simpleData() {

        assertEquals(58L, tested.part1(testData));

    }

    @Test
    void testWhoCanMove1() {
        var field = tested.initField("...>>>>>...");
        var whoCanMove = tested.findWhoCanMove(field, '>');
        assert whoCanMove.stream().filter(it -> it.getJ() == 0).count() == 1;

        field = tested.initField("...>>>>.>..");
        assert tested.findWhoCanMove(field, '>').stream().filter(it -> it.getJ() == 0).count() == 2;

    }

    @Test
    void testMoveStep() {
        var field = tested.initField("...>...\n" +
                ".......\n" +
                "......>\n" +
                "v.....>\n" +
                "......>\n" +
                ".......\n" +
                "..vvv..");

        tested.logField(field, "premove");

//        tested.performMoves(field, tested.findWhoCanMove(field, '>'));
//        tested.logField(field, "1>");
//        tested.applyBorders(field, '>');
//        tested.logField(field, "1b>");
//
//        tested.performMoves(field, tested.findWhoCanMove(field, 'v'));
//        tested.logField(field, "1v");
//        tested.applyBorders(field, 'v');
//        tested.logField(field, "1v>");
//
//        tested.performMoves(field, tested.findWhoCanMove(field, '>'));
//        tested.logField(field, "2>");
//        tested.applyBorders(field, '>');
//        tested.logField(field, "2b>");
//
//        tested.performMoves(field, tested.findWhoCanMove(field, 'v'));
//        tested.logField(field, "2v");
//        tested.applyBorders(field, 'v');
//        tested.logField(field, "2v>");


        tested.performOneStep(field);
        tested.logField(field, "1");
        tested.performOneStep(field);
        tested.logField(field, "2");
        tested.performOneStep(field);
        tested.logField(field, "3");
        tested.performOneStep(field);
        tested.logField(field, "4");


    }

    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(testData));

    }

}