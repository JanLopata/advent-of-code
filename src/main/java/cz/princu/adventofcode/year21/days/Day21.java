package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Day21 extends Day {
    public static void main(String[] args) throws IOException {
        new Day21().printParts();
    }

    private static final int FIELD_SIZE = 10;
    private static final SituationFactory SITUATION_FACTORY = new SituationFactory(FIELD_SIZE);
    private static final int WINNING_SCORE = 21;
    private static final int SIDES = 3;
    private static final int[] POLYNOM_COEFICIENTS = new int[]{0, 0, 0, 1, 3, 6, 7, 6, 3, 1};

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        int[] playerPositions = new int[2];
        long[] scores = new long[2];
        playerPositions[0] = Integer.parseInt(input[0].split(": ")[1]) - 1;
        playerPositions[1] = Integer.parseInt(input[1].split(": ")[1]) - 1;

        final int fieldSize = 10;
        final long maxScore = 1000;

        int onTurn = 0;
        Dice dice = new Dice();

        while (scores[0] < maxScore && scores[1] < maxScore) {
            playTurn(dice, playerPositions, scores, onTurn, fieldSize);
            onTurn = 1 - onTurn;
        }

        return scores[onTurn] * dice.uses;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        int[] playerPositions = new int[2];
        playerPositions[0] = Integer.parseInt(input[0].split(": ")[1]) - 1;
        playerPositions[1] = Integer.parseInt(input[1].split(": ")[1]) - 1;

        Map<Situation, Long> memory = new HashMap<>();

        memory.put(SITUATION_FACTORY.get(0, 0, playerPositions[0]), 1L);
        memory.put(SITUATION_FACTORY.get(0, 1, playerPositions[1]), 1L);

        countPossibleVariants(memory, SITUATION_FACTORY.get(6, 2, 1));

        for (int position = 0; position < FIELD_SIZE; position++) {
            for (int turn = 0; turn < WINNING_SCORE * 2; turn++) {
                for (int score = WINNING_SCORE; score < WINNING_SCORE + FIELD_SIZE; score++) {
                    countPossibleVariants(memory, SITUATION_FACTORY.get(score, turn, position));
                }
            }
        }

        var positive = memory.entrySet().stream()
                .filter(it -> it.getValue() > 0)
                .filter(it -> it.getKey().score >= WINNING_SCORE)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        long winCount = 0L;
        for (int score = WINNING_SCORE; score < WINNING_SCORE + FIELD_SIZE; score++) {
            for (int turn = 0; turn < WINNING_SCORE; turn += 2) {
                for (int endingPosition = 0; endingPosition < FIELD_SIZE; endingPosition++) {

                    winCount += memory.getOrDefault(SITUATION_FACTORY.get(score, turn, endingPosition), 0L);
                    winCount -= memory.getOrDefault(SITUATION_FACTORY.get(score, turn - 1, endingPosition), 0L);

                }
            }
        }


        return winCount;
    }

    private long countPossibleVariants(Map<Situation, Long> memory, Situation s) {

        if (s.illegal)
            return 0L;
        if (memory.containsKey(s))
            return memory.get(s);

        long possibleVariants = 0L;
        var previousScore = previousScore(s);

        for (int i = 1; i <= 3 * SIDES; i++) {
            possibleVariants += POLYNOM_COEFICIENTS[i] * countPossibleVariants(
                    memory, SITUATION_FACTORY.get(previousScore, s.turn - 2, s.position - i));
        }

        if (s.turn > 1) {
            possibleVariants *= 9 * 9;
        } else if (s.turn == 1) {
            possibleVariants *= 9;
        }

        memory.put(s, possibleVariants);
        return possibleVariants;

    }

    private void playTurn(Dice dice, int[] playerPositions, long[] scores, int onTurn, int fieldSize) {

        int moves = 0;
        for (int i = 0; i < 3; i++) {
            moves += dice.roll();
        }
        playerPositions[onTurn] += moves;
        playerPositions[onTurn] = playerPositions[onTurn] % fieldSize;

        scores[onTurn] += playerPositions[onTurn] + 1;

    }

    private static int previousScore(Situation situation) {
        return previousScore(situation.score, situation.position);
    }

    private static int previousScore(int score, int position) {
        return score - position - 1;
    }

    @RequiredArgsConstructor
    private static class SituationFactory {

        final int fieldSize;

        public Situation get(int score, int turn, int position) {
            boolean illegal = isIllegal(score, turn, position);
            return new Situation(score, turn, (position + fieldSize) % fieldSize, illegal);
        }

        private boolean isIllegal(int score, int turn, int position) {
            if (score < 0 || turn < 0 || (turn == 0 && score > 0))
                return true;

            return previousScore(score, position) >= WINNING_SCORE;
        }

    }

    @EqualsAndHashCode
    @RequiredArgsConstructor
    @ToString
    @Builder
    private static class Situation {

        final int score;
        final int turn;
        final int position;
        final boolean illegal;

    }

    private static class Dice {

        final int max = 100;
        int nextValue = 1;
        long uses = 0L;

        int roll() {

            uses += 1;
            var result = nextValue;
            nextValue += 1;
            if (nextValue > max)
                nextValue = 1;
            return result;
        }

    }


    @Override
    public int getDayNumber() {
        return 21;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
