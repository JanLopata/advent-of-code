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

        return countValidWithRemoval(dataArray);
    }

    Long countValidWithRemoval(long[] dataArray) {

        if (dataArray.length <= 4) {
            return countValidRemovals(Arrays.copyOf(dataArray, dataArray.length));
        }

        int middle = dataArray.length / 2;


        long[] left = Arrays.copyOfRange(dataArray, 0, middle + 1);
        long[] right = Arrays.copyOfRange(dataArray, middle, dataArray.length);

        long result = 1L;

        result *= countValidWithRemoval(left);
        result *= countValidWithRemoval(right);

        if (canBeRemoved(dataArray, middle)) {

            // removal
            long removalResult = 1L;
            left[left.length - 1] = dataArray[middle + 1];
            right = Arrays.copyOfRange(dataArray, middle + 1, dataArray.length);
            removalResult *= countValidWithRemoval(left);
            removalResult *= countValidWithRemoval(right);

            result += removalResult + 1;
        }

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

    private Long countValidRemovals(long[] smallData) {

        if (smallData.length > 4)
            throw new IllegalArgumentException("too big data");

        if (smallData.length < 3)
            return 1L;

        if (smallData.length == 3) {
            if (canBeRemoved(smallData, 1)) {
                return 2L;
            } else
                return 1L;
        }

        // length 4
        if (smallData[smallData.length - 1] - smallData[0] <= 3)
            return 4L;

        long result = 1;
        if (canBeRemoved(smallData, 1))
            result++;

        if (canBeRemoved(smallData, 2))
            result++;

        return result;

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
