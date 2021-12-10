package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day10Test {

    Day10 tested = new Day10();
    String testData = "[({(<(())[]>[[{[]{<()<>>\n" +
            "[(()[<>])]({[<{<<[]>>(\n" +
            "{([(<{}[<>[]}>{[]{[(<()>\n" +
            "(((({<>}<{<{<>}{[]{[]{}\n" +
            "[[<[([]))<([[{}[[()]]]\n" +
            "[{[{({}]{}}([{[{{{}}([]\n" +
            "{<[[]]>}<{[{[{[]{()[[[]\n" +
            "[<(<(<(<{}))><([]([]()\n" +
            "<{([([[(<>()){}]>(<<{{\n" +
            "<{([{{}}[<[[[<>{}]]]>[]]";

    String smallData = "(]\n{()()()>\n(((()))}\n<([]){()}[{}])";

    @Test
    void testPart1_simpleData() {

        assertEquals(26397L, tested.part1(testData));

    }


    @Test
    void testPart1_lineScore() {

//        assertEquals(57L, tested.getLineScore("(]"));
//        assertEquals(25137L, tested.getLineScore("{()()()>"));
//        assertEquals(1197L, tested.getLineScore("(((()))}"));
//        assertEquals(3L, tested.getLineScore("<([]){()}[{}])"));

        assertEquals(1197L, tested.getLineScoreIfCorrupted("{([(<{}[<>[]}>{[]{[(<()>"));

    }

    @Test
    void testPart2_one_simpleData() {

        assertEquals(288957L, tested.part2(testData));

    }

}