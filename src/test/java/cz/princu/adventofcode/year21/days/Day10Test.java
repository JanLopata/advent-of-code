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
    void testPart1_smallData() {

        assertEquals((long) 3 + 57 + 1197 + 25137, tested.part1(smallData));

    }

    @Test
    void testPart1_lineScore() {

        assertEquals(57L, tested.getLineScoreIfCorrupted("(]"));
        assertEquals(25137L, tested.getLineScoreIfCorrupted("{()()()>"));
        assertEquals(1197L, tested.getLineScoreIfCorrupted("(((()))}"));
        assertEquals(3L, tested.getLineScoreIfCorrupted("<([]){()}[{}])"));

        assertEquals(1197L, tested.getLineScoreIfCorrupted("{([(<{}[<>[]}>{[]{[(<()>"));

    }

    @Test
    void testPart2_simpleData() {

        assertEquals(288957L, tested.part2(testData));

    }

}