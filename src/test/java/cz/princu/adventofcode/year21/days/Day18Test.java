package cz.princu.adventofcode.year21.days;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day18Test {

    final String numberToParse =
            "[1,2]\n" +
            "[[1,2],3]\n" +
            "[9,[8,7]]\n" +
            "[[1,9],[8,5]]\n" +
            "[[[[1,2],[3,4]],[[5,6],[7,8]]],9]\n" +
            "[[[9,[3,8]],[[0,9],6]],[[[3,7],[4,9]],3]]\n" +
            "[[[[1,3],[5,3]],[[1,3],[8,7]]],[[[4,9],[6,9]],[[8,2],[7,3]]]]";

    final String forSum = "[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]\n" +
            "[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]\n" +
            "[[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]\n" +
            "[[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]\n" +
            "[7,[5,[[3,8],[1,4]]]]\n" +
            "[[2,[2,2]],[8,[8,1]]]\n" +
            "[2,9]\n" +
            "[1,[[[9,3],9],[[9,0],[0,7]]]]\n" +
            "[[[5,[7,4]],7],1]\n" +
            "[[[[4,2],2],6],[8,7]]";

    Day18 tested = new Day18();

    @Test
    void testPart1_simpleData() {

        assertEquals(0L, tested.part1(numberToParse));

    }


    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "[[[[[9,8],1],2],3],4];null",
            "[7,[6,[5,[4,[3,2]]]]];4",
            "[[6,[5,[4,[3,2]]]],1];4",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]];1",
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]];4"
    })
    void findRegularNumberLeftTest(String input, String expectedResult) {

        Day18.Term result = new Day18.Term();
        tested.parseTerm(input, result, new AtomicInteger());
        var oneToExplode = result.findOneToExplode(0);
        tested.representTerm(result);
        tested.representTerm(oneToExplode);
        var regularNumberLeft = oneToExplode.findRegularNumberLeft();
        var actual = tested.representTerm(regularNumberLeft);
        assertEquals(expectedResult, actual != null ? actual : "null");

    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "[[[[[9,8],1],2],3],4];1",
            "[7,[6,[5,[4,[3,2]]]]];null",
            "[[6,[5,[4,[3,2]]]],1];1",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]];6",
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]];null"
    })
    void findRegularNumberRightTest(String input, String expectedResult) {

        Day18.Term result = new Day18.Term();
        tested.parseTerm(input, result, new AtomicInteger());
        var oneToExplode = result.findOneToExplode(0);
        tested.representTerm(result);
        tested.representTerm(oneToExplode);
        var regularNumberRight = oneToExplode.findRegularNumberRight();
        var actual = tested.representTerm(regularNumberRight);
        assertEquals(expectedResult, actual != null ? actual : "null");

    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "[[[[[9,8],1],2],3],4];[[[[0,9],2],3],4]",
            "[7,[6,[5,[4,[3,2]]]]];[7,[6,[5,[7,0]]]]",
            "[[6,[5,[4,[3,2]]]],1];[[6,[5,[7,0]]],3]",
            "[[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]];[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]]",
            "[[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]];[[3,[2,[8,0]]],[9,[5,[7,0]]]]"
    })
    void explodeTest(String input, String expectedResult) {

        Day18.Term result = new Day18.Term();
        tested.parseTerm(input, result, new AtomicInteger());
        var oneToExplode = result.findOneToExplode(0);
        tested.representTerm(result);
        tested.representTerm(oneToExplode);
        oneToExplode.explodeHere();
        var afterExplosionText = tested.representTerm(result);
        assertEquals(expectedResult, afterExplosionText);
    }

    @ParameterizedTest
    @CsvSource(delimiter = ';', value = {
            "10;[5,5]",
            "11;[5,6]",
            "12;[6,6]"})
    void splitTest(String input, String expectedResult) {

        Day18.Term term = new Day18.Term();
        term.setNumber(Integer.parseInt(input));
        term.split();
        var afterSplitText = tested.representTerm(term);
        assertEquals(expectedResult, afterSplitText);
    }

    @Test
    void reduceTest() {
        Day18.Term term = new Day18.Term();
        tested.parseTerm("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]", term, new AtomicInteger());

        term.reduce();
        var afterSplitText = tested.representTerm(term);
        assertEquals("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]", afterSplitText);

    }


    @Test
    void testPart2_simpleData() {

        assertEquals(0L, tested.part2(numberToParse));

    }

    @Test
    void testExplode() {

        Day18.Term term = new Day18.Term();


    }


}