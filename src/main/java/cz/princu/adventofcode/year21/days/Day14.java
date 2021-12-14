package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public class Day14 extends Day {
    public static void main(String[] args) throws IOException {
        new Day14().printParts();
    }

    @Override
    public Object part1(String data) {

        return solveForGivenIterationCount(data, 10);
    }

    @Override
    public Object part2(String data) {

        return solveForGivenIterationCount(data, 40);

    }

    private long solveForGivenIterationCount(String data, int iterationCount) {
        String[] input = data.split("\n\n");

        String start = input[0];
        String[] rules = input[1].split("\n");
        HashMap<Pair<Character, Character>, Character> transformations = new HashMap<>();
        for (String rule : rules) {
            var split = rule.split(" -> ");
            var chars = split[0].toCharArray();
            transformations.put(Pair.of(chars[0], chars[1]), split[1].charAt(0));
        }

        HashMap<Pair<Character, Character>, AtomicLong> pairCounts = new HashMap<>();
        var startChars = start.toCharArray();
        for (int i = 0; i < startChars.length - 1; i++) {
            var pair = Pair.of(startChars[i], startChars[i + 1]);
            addCount(pairCounts, pair, 1);
        }

        for (int i = 0; i < iterationCount; i++) {
            doStep(pairCounts, transformations);
        }

        return countMostAndLeastUsedElements(pairCounts, start);
    }

    private void doStep(HashMap<Pair<Character, Character>, AtomicLong> pairCounts, HashMap<Pair<Character, Character>, Character> transformations) {


        HashMap<Pair<Character, Character>, AtomicLong> newCounts = new HashMap<>();
        for (Map.Entry<Pair<Character, Character>, AtomicLong> pairLongEntry : pairCounts.entrySet()) {

            var pair = pairLongEntry.getKey();
            var target = transformations.get(pair);

            addCount(newCounts, Pair.of(pair.getLeft(), target), pairLongEntry.getValue().get());
            addCount(newCounts, Pair.of(target, pair.getRight()), pairLongEntry.getValue().get());

        }

        pairCounts.clear();
        pairCounts.putAll(newCounts);

    }

    private void addCount(HashMap<Pair<Character, Character>, AtomicLong> pairCounts, Pair<Character, Character> pair, long delta) {
        pairCounts.computeIfAbsent(pair, (p) -> new AtomicLong(0));
        pairCounts.get(pair).addAndGet(delta);
    }

    private long countMostAndLeastUsedElements(HashMap<Pair<Character, Character>, AtomicLong> pairCounts, String start) {

        HashMap<Character, AtomicLong> characterIncidence = new HashMap<>();

        for (Map.Entry<Pair<Character, Character>, AtomicLong> pairAtomicLongEntry : pairCounts.entrySet()) {

            char c = pairAtomicLongEntry.getKey().getLeft();

            characterIncidence.computeIfAbsent(c, ign -> new AtomicLong(0));
            characterIncidence.get(c).addAndGet(pairAtomicLongEntry.getValue().get());

            c = pairAtomicLongEntry.getKey().getRight();
            characterIncidence.computeIfAbsent(c, ign -> new AtomicLong(0));
            characterIncidence.get(c).addAndGet(pairAtomicLongEntry.getValue().get());

        }

        // starting and ending chars are not in pair twice
        characterIncidence.get(start.charAt(0)).incrementAndGet();
        characterIncidence.get(start.charAt(start.length() - 1)).incrementAndGet();

        var max = characterIncidence.values().stream().mapToLong(it -> it.get()).max().orElse(-1);
        var min = characterIncidence.values().stream().mapToLong(it -> it.get()).min().orElse(-1);

        return (max - min) / 2;

    }


    @Override
    public int getDayNumber() {
        return 14;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
