package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class Day21 extends Day {
    public static void main(String[] args) throws IOException {
        new Day21().printParts();
    }

    private static final int FIELD_SIZE = 10;
    private static final int WINNING_SCORE = 21;
    private static final int SIDES = 3;

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

        Map<OverallSituation, Pair<Long, Long>> memory = new HashMap<>();

        var result = makeAGame(memory, new OverallSituation(
                new Situation(0, playerPositions[0]),
                new Situation(0, playerPositions[1]),
                0));

        return result.getLeft() > result.getRight() ? result.getLeft() : result.getRight();

    }

    private Pair<Long, Long> makeAGame(Map<OverallSituation, Pair<Long, Long>> memory, OverallSituation overallSituation) {

        if (memory.containsKey(overallSituation))
            return memory.get(overallSituation);

        if (overallSituation.playerOne.score >= WINNING_SCORE) {
            var firstWon = Pair.of(1L, 0L);
            memory.put(overallSituation, firstWon);
            return firstWon;
        }
        if (overallSituation.playerTwo.score >= WINNING_SCORE) {
            var secondWon = Pair.of(0L, 1L);
            memory.put(overallSituation, secondWon);
            return secondWon;
        }

        long playerOneWins = 0L;
        long playerTwoWins = 0L;

        for (int i = 1; i <= SIDES; i++) {
            for (int j = 1; j <= SIDES; j++) {
                for (int k = 1; k <= SIDES; k++) {

                    int shift = i + j + k;
                    if (overallSituation.playerOnTurn == 0) {

                        var newPos = (overallSituation.playerOne.position + shift) % FIELD_SIZE;
                        var newScore = overallSituation.playerOne.score + newPos + 1;
                        var wonInfo = makeAGame(memory, new OverallSituation(
                                new Situation(newScore, newPos),
                                overallSituation.playerTwo,
                                1 - overallSituation.playerOnTurn));
                        playerOneWins += wonInfo.getLeft();
                        playerTwoWins += wonInfo.getRight();

                    } else {

                        var newPos = (overallSituation.playerTwo.position + shift) % FIELD_SIZE;
                        var newScore = overallSituation.playerTwo.score + newPos + 1;
                        var wonInfo = makeAGame(memory, new OverallSituation(
                                overallSituation.playerOne,
                                new Situation(newScore, newPos),
                                1 - overallSituation.playerOnTurn));
                        playerOneWins += wonInfo.getLeft();
                        playerTwoWins += wonInfo.getRight();
                    }
                }
            }
        }

        var result = Pair.of(playerOneWins, playerTwoWins);
        memory.put(overallSituation, result);
        return result;

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

    @EqualsAndHashCode
    @RequiredArgsConstructor
    @ToString
    @Builder
    private static class Situation {

        final int score;
        final int position;

    }

    @RequiredArgsConstructor
    @EqualsAndHashCode
    private static class OverallSituation {

        final Situation playerOne;
        final Situation playerTwo;
        final int playerOnTurn;

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
