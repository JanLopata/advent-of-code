package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day23 extends Day {
    public static void main(String[] args) throws IOException {
        new Day23().printParts();
    }

    @Setter
    private int part1Rounds = 100;

    @Setter
    private int part2Rounds = 10_000_000;

    @Override
    public Object part1(String data) {

        Map<Integer, Nexter> nexterMap = new HashMap<>();

        Nexter first = null;
        Nexter previous = null;
        Nexter last = null;
        for (char c : data.toCharArray()) {

            Nexter nexter = new Nexter(Integer.parseInt(String.valueOf(c)));
            nexterMap.put(nexter.getValue(), nexter);

            if (first == null)
                first = nexter;

            if (previous != null)
                previous.setNext(nexter);

            previous = nexter;
            last = nexter;
        }

        assert last != null;
        last.setNext(first);

        int max = nexterMap.keySet().stream()
                .max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException("no max found"));
        Nexter current = first;
        for (int i = 0; i < part1Rounds; i++) {

            current = performMove(nexterMap, current, max);

        }

        List<Integer> resultList = new ArrayList<>(max);

        Nexter work = nexterMap.get(1);
        for (int i = 0; i < nexterMap.size(); i++) {
            resultList.add(work.getValue());
            work = work.getNext();
        }

        log.info("result: {}", resultList.stream().map(String::valueOf).collect(Collectors.joining(" ")));

        return resultList.stream().skip(1).map(String::valueOf).collect(Collectors.joining());
    }

    private Nexter performMove(Map<Integer, Nexter> cups, Nexter current, int max) {

        List<Nexter> takenAway = new ArrayList<>(3);

        Nexter work = current;
        for (int i = 0; i < 3; i++) {
            work = work.getNext();
            takenAway.add(work);
        }

        disconnect(current, takenAway);

        Nexter destination = chooseDestination(cups, current, takenAway, max);

        connect(destination, takenAway);

        return current.getNext();

    }

    private Nexter chooseDestination(Map<Integer, Nexter> cups, Nexter current, List<Nexter> takenAway, int max) {

        Set<Integer> ignored = takenAway.stream().map(Nexter::getValue).collect(Collectors.toSet());

        int value = current.getValue() - 1;
        while (true) {

            if (cups.containsKey(value) && !ignored.contains(value))
                return cups.get(value);

            value--;
            if (value < 0)
                value = max;
        }
    }

    private void connect(Nexter destination, List<Nexter> takenAway) {
        final Nexter right = destination.getNext();

        destination.setNext(takenAway.get(0));
        takenAway.get(2).setNext(right);

    }

    private void disconnect(Nexter current, List<Nexter> takenAway) {

        current.setNext(takenAway.get(2).getNext());
        takenAway.get(2).setNext(null);

    }

    @Override
    public Object part2(String data) {

        Map<Integer, Nexter> nexterMap = new HashMap<>();

        Nexter first = null;
        Nexter previous = null;
        Nexter last = null;
        for (char c : data.toCharArray()) {

            Nexter nexter = new Nexter(Integer.parseInt(String.valueOf(c)));
            nexterMap.put(nexter.getValue(), nexter);

            if (first == null)
                first = nexter;

            if (previous != null)
                previous.setNext(nexter);

            previous = nexter;
            last = nexter;
        }
        assert last != null;
        int max = nexterMap.keySet().stream()
                .max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException("no max found"));

        for (int i = max + 1; i <= 1_000_000; i++) {
            Nexter nexter = new Nexter(i);
            nexterMap.put(nexter.getValue(), nexter);
            previous.setNext(nexter);

            previous = nexter;
            last = nexter;
        }

        max = nexterMap.keySet().stream()
                .max(Comparator.naturalOrder()).orElseThrow(() -> new IllegalStateException("no max found"));

        last.setNext(first);

        Nexter current = first;
        for (int i = 0; i < part2Rounds; i++) {

            current = performMove(nexterMap, current, max);

        }

        Nexter work = nexterMap.get(1);

        return work.getNext().getValue() * (long) work.getNext().getNext().getValue();

    }


    @Getter
    @Setter
    @ToString(of = "value")
    @RequiredArgsConstructor
    private static class Nexter {

        private final int value;
        private Nexter next;

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
