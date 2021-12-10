package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class Day10 extends Day {
    public static void main(String[] args) throws IOException {
        new Day10().printParts();
    }

    static final HashMap<Character, Character> pairings = new HashMap<>();
    static final HashMap<Character, Long> errorScore = new HashMap<>();
    static final HashMap<Character, Long> autocompleteScore = new HashMap<>();


    static {
        pairings.put('(', ')');
        pairings.put('[', ']');
        pairings.put('{', '}');
        pairings.put('<', '>');
        errorScore.put(')', 3L);
        errorScore.put(']', 57L);
        errorScore.put('}', 1197L);
        errorScore.put('>', 25137L);
        autocompleteScore.put(')', 1L);
        autocompleteScore.put(']', 2L);
        autocompleteScore.put('}', 3L);
        autocompleteScore.put('>', 4L);

    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        long score = 0L;

        for (String line : input) {
            long lineScore = getLineScoreIfCorrupted(line);

            score += lineScore;
        }

        return score;

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        List<Long> scoreList = new ArrayList<>();

        for (String line : input) {
            long lineScore = getLineScoreIfAutoCompleted(line);

            if (lineScore > 0)
                scoreList.add(lineScore);
        }

        scoreList.sort(Comparator.naturalOrder());
        return scoreList.get(scoreList.size() / 2);
    }

    public long getLineScoreIfCorrupted(String line) {

        char[] chars = line.toCharArray();
        LinkedList<Character> expected = new LinkedList<>();

        for (char c : chars) {
            if (pairings.containsKey(c)) {
                // opening
                char closing = pairings.get(c);
                expected.add(closing);
            } else {
                // closing
                var expectedChar = expected.removeLast();
                if (expectedChar != c) {
                    return errorScore.get(c);
                }
            }
        }

        return 0;
    }

    public long getLineScoreIfAutoCompleted(String line) {

        char[] chars = line.toCharArray();
        LinkedList<Character> expected = new LinkedList<>();

        for (char c : chars) {
            if (pairings.containsKey(c)) {
                // opening
                char closing = pairings.get(c);
                expected.add(closing);
            } else {
                // closing
                var expectedChar = expected.removeLast();
                if (expectedChar != c) {
                    return 0;
                }
            }
        }

        // autocompletion:
        long score = 0L;
        while (!expected.isEmpty()) {

            var expectedChar = expected.removeLast();
            score *= 5;
            score += autocompleteScore.get(expectedChar);

        }

        return score;
    }



    @Override
    public int getDayNumber() {
        return 10;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
