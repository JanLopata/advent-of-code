package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Day21 extends Day {
    public static void main(String[] args) throws IOException {
        new Day21().printParts();
    }

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

    private void playTurn(Dice dice, int[] playerPositions, long[] scores, int onTurn, int fieldSize) {

        int moves = 0;
        for (int i = 0; i < 3; i++) {
            moves += dice.roll();
        }
        playerPositions[onTurn] += moves;
        playerPositions[onTurn] = playerPositions[onTurn] % fieldSize;

        scores[onTurn] += playerPositions[onTurn] + 1;

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
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
