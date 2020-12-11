package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Day10 extends Day {

    public static void main(String[] args) throws IOException {
        new Day10().printParts();
    }

    @Override
    public Object part1(String data) {

        LinkedList<Long> adaptersSorted = getAdaptersSorted(data);

        Map<Long, Integer> differencesCountMap = new HashMap<>();

        for (int i = 0; i < adaptersSorted.size() - 1; i++) {

            long diff = adaptersSorted.get(i + 1) - adaptersSorted.get(i);
            differencesCountMap.putIfAbsent(diff, 0);

            differencesCountMap.put(diff, differencesCountMap.get(diff) + 1);

        }


        return (long)
                differencesCountMap.getOrDefault(1L, 0)
                * differencesCountMap.getOrDefault(3L, 0);

    }

    private LinkedList<Long> getAdaptersSorted(String data) {
        String[] lines = data.split("\n");

        LinkedList<Long> adaptersSorted = new LinkedList<>();
        adaptersSorted.addFirst(0L);

        Arrays.stream(lines)
                .mapToLong(Long::parseLong)
                .sorted()
                .forEach(adaptersSorted::add);

        adaptersSorted.add(adaptersSorted.getLast() + 3);
        return adaptersSorted;
    }


    @Override
    public Object part2(String data) {

        LinkedList<Long> adaptersSorted = getAdaptersSorted(data);

        long[] dataArray = adaptersSorted.stream().mapToLong(it -> it).toArray();
        List<Integer> solidPointIndices = findSolidPoints(dataArray);

        long result = 1L;

        for (int i = 0; i < solidPointIndices.size() - 1; i++) {

            int solidPointDiff = solidPointIndices.get(i + 1) - solidPointIndices.get(i);

            if (solidPointDiff == 1)
                result *= 1;

            if (solidPointDiff == 2)
                result *= 2;

            if (solidPointDiff == 3)
                result *= 4;

            if (solidPointDiff == 4)
                result *= 7;

            if (solidPointDiff < 1 || solidPointDiff > 4)
                throw new IllegalArgumentException("do not know");
        }

        return result;
    }

    private List<Integer> findSolidPoints(long[] dataArray) {


        List<Integer> result = new ArrayList<>();
        result.add(0);
        for (int i = 1; i < dataArray.length - 1; i++) {

            if (!canBeRemoved(dataArray, i))
                result.add(i);
        }

        result.add(dataArray.length - 1);

        return result;

    }


    void generateValid(long[] data) {

        Set<String> validSet = new HashSet<>();

        int n = data.length;

        for (int i = 0; i < (1 << n); i++) {

            List<Long> myList = new ArrayList<>();

            myList.add(data[0]);
            for (int j = 1; j < n - 1; j++) {
                if ((i & (1 << j)) > 0) {
                    myList.add(data[j]);
                }
            }
            myList.add(data[n - 1]);

            boolean valid = true;
            for (int j = 0; j < myList.size() - 1; j++) {
                if (myList.get(j + 1) - myList.get(j) > 3) {
                    valid = false;
                    break;
                }
            }
            if (valid)
                validSet.add(myList.stream().map(Object::toString).collect(Collectors.joining(", ")));

        }

        System.out.println(validSet.size());
        validSet.forEach(System.out::println);

    }


    private boolean canBeRemoved(long[] data, int indexToRemove) {

        return data[indexToRemove + 1] - data[indexToRemove - 1] <= 3;

    }


    @Override
    public int getDayNumber() {
        return 10;
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Builder
    @Getter
    @ToString
    static class BagRule {
        private final String outerBag;
        private final Map<String, Integer> innerBagsMap;
    }

}
