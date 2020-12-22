package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

@Slf4j
public class Day22 extends Day {
    public static void main(String[] args) throws IOException {
        new Day22().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        final LinkedList<Long> player1Deck = parsePlayerDeck(input[0]);
        final LinkedList<Long> player2Deck = parsePlayerDeck(input[1]);

        performOneGame(player1Deck, player2Deck);

        return computeResultValue(player1Deck, player2Deck);
    }

    private void performOneGame(LinkedList<Long> player1Deck, LinkedList<Long> player2Deck) {
        int step = 0;
        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {

            step++;

            Long p1Value = player1Deck.pollFirst();
            Long p2Value = player2Deck.pollFirst();

            if (p1Value.equals(p2Value)) {
                log.info("Step {} - {} vs {} - equals", step, p1Value, p2Value);
                player1Deck.add(p1Value);
                player2Deck.add(p2Value);
                continue;
            }

            boolean firstWon = p1Value > p2Value;

            if (firstWon) {
                log.info("Step {} - {} vs {} first won", step, p1Value, p2Value);
                player1Deck.add(p1Value);
                player1Deck.add(p2Value);
            } else {
                log.info("Step {} - {} vs {} second won", step, p1Value, p2Value);
                player2Deck.add(p2Value);
                player2Deck.add(p1Value);
            }

        }
    }

    private long computeResultValue(LinkedList<Long> player1Deck, LinkedList<Long> player2Deck) {
        long result = 0;

        player1Deck.addAll(player2Deck);
        int i = player1Deck.size();
        for (Long number : player1Deck) {
            result += i * number;
            i--;
        }

        return result;
    }

    private LinkedList<Long> parsePlayerDeck(String s) {
        LinkedList<Long> playerOneDeck = new LinkedList<>();
        Arrays.stream(s.split("\n")).skip(1).map(Long::parseLong).forEach(playerOneDeck::add);
        return playerOneDeck;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 22;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
