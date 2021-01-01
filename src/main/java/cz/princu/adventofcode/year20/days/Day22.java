package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
                playerOneWon(player1Deck, step, 0, p1Value, p2Value);
            } else {
                playerTwoWon(player2Deck, step, 0, p1Value, p2Value);
            }

        }
    }

    private void playerTwoWon(LinkedList<Long> player2Deck, int step, int depth, Long p1Value, Long p2Value) {
//        log.info("Step {} depth {} - {} vs {} second won", step, depth, p1Value, p2Value);
        player2Deck.add(p2Value);
        player2Deck.add(p1Value);
    }

    private void playerOneWon(LinkedList<Long> player1Deck, int step, int depth, Long p1Value, Long p2Value) {
//        log.info("Step {} depth {} - {} vs {} first won", step, depth, p1Value, p2Value);
        player1Deck.add(p1Value);
        player1Deck.add(p2Value);
    }

    private void playerOneWonTheGame(LinkedList<Long> player1Deck, LinkedList<Long> player2Deck, int step, int depth) {
        log.info("Step {} depth {} - {} vs {} first won the game", step, depth);

        player1Deck.addAll(player2Deck);
        player2Deck.clear();
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

        String[] input = data.split("\n\n");

        final LinkedList<Long> player1Deck = parsePlayerDeck(input[0]);
        final LinkedList<Long> player2Deck = parsePlayerDeck(input[1]);

        KnownConfigurationChecker checker = new KnownConfigurationChecker();

        playRecursiveOneGame(player1Deck, player2Deck, checker, 0);

        return computeResultValue(player1Deck, player2Deck);
    }

    private boolean playRecursiveOneGame(LinkedList<Long> player1Deck,
                                      LinkedList<Long> player2Deck,
                                      KnownConfigurationChecker checker,
                                      int depth) {
        int step = 0;
        boolean result = true;
        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {
            step++;
            result = playRecursively(player1Deck, player2Deck, checker, step, depth);
        }

        return result;
    }

    /**
     * returns true when player 1 won
     *
     * @param player1Deck
     * @param player2Deck
     * @param checker
     * @param depth
     * @return
     */
    private boolean playRecursively(LinkedList<Long> player1Deck, LinkedList<Long> player2Deck, KnownConfigurationChecker checker, int step, int depth) {

        if (checker.testKnown(player1Deck, player2Deck)) {
            playerOneWonTheGame(player1Deck, player2Deck, step, depth);
            return true;
        }

        Long p1Value = player1Deck.pollFirst();
        Long p2Value = player2Deck.pollFirst();

        if (p1Value <= player1Deck.size() && p2Value <= player2Deck.size()) {

            boolean player1Wins = playRecursiveOneGame(
                    new LinkedList<>(player1Deck),
                    new LinkedList<>(player2Deck),
                    new KnownConfigurationChecker(),
                    depth + 1);

            if (player1Wins) {
                playerOneWon(player1Deck, step, depth, p1Value, p2Value);
            } else {
                playerTwoWon(player2Deck, step, depth, p1Value, p2Value);
            }
            return player1Wins;

        } else {
            boolean firstWon = p1Value > p2Value;

            if (firstWon) {
                playerOneWon(player1Deck, step, depth, p1Value, p2Value);
            } else {
                playerTwoWon(player2Deck, step, depth, p1Value, p2Value);
            }
            return firstWon;
        }

    }

    @NoArgsConstructor
    private static class KnownConfigurationChecker {

        private final Set<List<Long>> player1Known = new HashSet<>();
        private final Set<List<Long>> player2Known = new HashSet<>();

        public boolean testKnown(List<Long> player1Deck, List<Long> player2Deck) {

            final boolean result = player1Known.contains(player1Deck) && player2Known.contains(player2Deck);

            player1Known.add(new LinkedList<>(player1Deck));
            player2Known.add(new LinkedList<>(player2Deck));

            return result;

        }
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
