package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day23 extends Day {
    public static void main(String[] args) throws IOException {
        new Day23().printParts();
    }

    @Setter
    private int rounds = 100;

    @Override
    public Object part1(String data) {

        LinkedList<Integer> cups = new LinkedList<>();

        for (char c : data.toCharArray()) {
            cups.add(Integer.parseInt(String.valueOf(c)));
        }

        for (int i = 0; i < rounds; i++) {

            performMove(cups);

        }

        rotate(cups, 1);

        log.info("result: {}", cups.stream().map(String::valueOf).collect(Collectors.joining(" ")));


        return cups.stream().skip(1).map(String::valueOf).collect(Collectors.joining());
    }

    private void rotate(LinkedList<Integer> cups, int current) {

        while (cups.getFirst() != current) {
            Integer first = cups.pollFirst();
            cups.add(first);
        }

    }

    // current value is always first
    private void performMove(LinkedList<Integer> cups) {

//        log.info("{}", cups.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        Integer first = cups.pollFirst();

        LinkedList<Integer> three = new LinkedList<>();
        for (int i = 0; i < 3; i++) {
            three.add(cups.pollFirst());
        }

        int destination = chooseDestinationCup(cups, first - 1);
        cups.addLast(first);

        rotate(cups, destination);
        cups.removeFirst();

        for (int i = 0; i < 3; i++) {
            cups.addFirst(three.pollLast());
        }

        cups.addFirst(destination);

        rotate(cups, first);
        rotate(cups, cups.get(1));

//        log.info("{} destination {}",
//                cups.stream().map(String::valueOf).collect(Collectors.joining(" ")),
//                destination);

    }

    private int chooseDestinationCup(LinkedList<Integer> cups, Integer initDestination) {

        final Set<Integer> existing = cups.stream().collect(Collectors.toSet());

        int destination = initDestination;
        while (true) {

            if (existing.contains(destination))
                return destination;

            if (destination <= 1)
                destination = 10;

            destination--;
        }

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        LinkedList<Integer> cups = new LinkedList<>();

        for (char c : data.toCharArray()) {
            cups.add(Integer.parseInt(String.valueOf(c)));
        }

        Set<Integer> used = new HashSet<>(cups);
        for (int i = 1; i <= 1_000_000; i++) {

            if (!used.contains(i))
                cups.add(i);
        }

        for (int i = 0; i < rounds; i++) {

            log.info("Move {}", i);
            performMove(cups);

        }

        return cups.get(1) * (long) cups.get(2);
    }


    @Override
    public int getDayNumber() {
        return 23;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
