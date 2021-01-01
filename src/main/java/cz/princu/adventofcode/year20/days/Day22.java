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

        final LinkedList<Integer> player1Deck = parsePlayerDeck(input[0]);
        final LinkedList<Integer> player2Deck = parsePlayerDeck(input[1]);

        performOneGame(player1Deck, player2Deck);

        return computeResultValue(player1Deck, player2Deck);
    }

    private void performOneGame(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck) {
        int step = 0;
        while (!(player1Deck.isEmpty() || player2Deck.isEmpty())) {

            step++;

            Integer p1Value = player1Deck.pollFirst();
            Integer p2Value = player2Deck.pollFirst();

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

    private void playerTwoWon(LinkedList<Integer> player2Deck, int step, int depth, Integer p1Value, Integer p2Value) {
//        log.info("Step {} depth {} - {} vs {} second won", step, depth, p1Value, p2Value);
        player2Deck.add(p2Value);
        player2Deck.add(p1Value);
    }

    private void playerOneWon(LinkedList<Integer> player1Deck, int step, int depth, Integer p1Value, Integer p2Value) {
//        log.info("Step {} depth {} - {} vs {} first won", step, depth, p1Value, p2Value);
        player1Deck.add(p1Value);
        player1Deck.add(p2Value);
    }

    private void playerOneWonTheGame(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck, int step, int depth) {
        log.info("Step {} depth {} - first won the game", step, depth);

        player1Deck.addAll(player2Deck);
        player2Deck.clear();
    }

    private long computeResultValue(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck) {
        long result = 0;

        player1Deck.addAll(player2Deck);
        long i = player1Deck.size();
        for (Integer number : player1Deck) {
            result += i * number;
            i--;
        }

        return result;
    }

    private LinkedList<Integer> parsePlayerDeck(String s) {
        LinkedList<Integer> playerOneDeck = new LinkedList<>();
        Arrays.stream(s.split("\n")).skip(1).map(Integer::parseInt).forEach(playerOneDeck::add);
        return playerOneDeck;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n\n");

        final LinkedList<Integer> player1Deck = parsePlayerDeck(input[0]);
        final LinkedList<Integer> player2Deck = parsePlayerDeck(input[1]);

        KnownConfigurationChecker checker = new KnownConfigurationChecker();

        playRecursiveOneGame(player1Deck, player2Deck, checker, 0);

        return computeResultValue(player1Deck, player2Deck);
    }

    private boolean playRecursiveOneGame(LinkedList<Integer> player1Deck,
                                      LinkedList<Integer> player2Deck,
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
    private boolean playRecursively(LinkedList<Integer> player1Deck, LinkedList<Integer> player2Deck, KnownConfigurationChecker checker, int step, int depth) {

        if (checker.testKnown(player1Deck, player2Deck)) {
            playerOneWonTheGame(player1Deck, player2Deck, step, depth);
            return true;
        }

        Integer p1Value = player1Deck.pollFirst();
        Integer p2Value = player2Deck.pollFirst();

        if (p1Value <= player1Deck.size() && p2Value <= player2Deck.size()) {

            boolean player1Wins = playRecursiveOneGame(
                    new LinkedList<>(player1Deck.subList(0, p1Value)),
                    new LinkedList<>(player2Deck.subList(0, p2Value)),
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

        private final Set<List<Integer>> player1Known = new HashSet<>();
        private final Set<List<Integer>> player2Known = new HashSet<>();

        public boolean testKnown(List<Integer> player1Deck, List<Integer> player2Deck) {

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
