package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.lang.Integer.valueOf;

@Slf4j
public class Day03 extends Day {
    public static void main(String[] args) throws IOException {
        new Day03().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        int dataSize = input[0].length();
        Map<Integer, AtomicInteger> oneBitCounts = getOneBitCounts(input);

        List<Integer> gammaBits = new ArrayList<>();
        List<Integer> epsilonBits = new ArrayList<>();

        for (int i = 0; i < dataSize; i++) {

            boolean mostCommonOne = isMostCommonOne(oneBitCounts.get(i), input.length);

            gammaBits.add(mostCommonOne ? 1 : 0);
            epsilonBits.add(mostCommonOne ? 0 : 1);

        }

        long gamma = binaryToNumber(gammaBits);
        long epsilon = binaryToNumber(epsilonBits);


        return epsilon * gamma;
    }

    private Map<Integer, AtomicInteger> getOneBitCounts(List<String> input) {

        int dataSize = input.get(0).length();

        Map<Integer, AtomicInteger> oneBitCounts = new HashMap<>();
        for (int i = 0; i < dataSize; i++) {
            oneBitCounts.put(i, new AtomicInteger());
        }

        for (int i = 0; i < input.size(); i++) {
            for (int j = 0; j < dataSize; j++) {
                String bitStr = input.get(i).substring(j, j + 1);
                if (bitStr.startsWith("1")) {
                    oneBitCounts.get(j).incrementAndGet();
                }
            }
        }
        return oneBitCounts;
    }

    private Map<Integer, AtomicInteger> getOneBitCounts(String[] input) {
        return getOneBitCounts(Arrays.stream(input).collect(Collectors.toList()));
    }

    private long binaryToNumber(List<Integer> epsilonBits) {

        int counter = 1;
        long result = 0;
        for (int i = 0; i < epsilonBits.size(); i++) {
            int g = epsilonBits.size() - i - 1;
            result += (long) counter * epsilonBits.get(g);
            counter *= 2;
        }

        return result;
    }

    private boolean isMostCommonOne(AtomicInteger atomicInteger, int length) {
        return atomicInteger.get() >= length / 2;
    }

    private int compareOneToZero(AtomicInteger atomicInteger, int length) {

        int ones = atomicInteger.get();
        int zeros = length - ones;

        if (ones > zeros)
            return 1;
        if (zeros > ones)
            return -1;
        return 0;

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        String o2 = filterOutOneString(input, false);
        String co2 = filterOutOneString(input, true);

        return Long.valueOf(o2, 2) * valueOf(co2, 2);
    }

    private String filterOutOneString(String[] input, boolean flip) {

        List<String> dataList = Arrays.stream(input).collect(Collectors.toList());

        int currentIndex = 0;
        Set<Integer> removedIndices = new HashSet<>();
        while (true) {

            Map<Integer, Integer> oneToZeroCompare = oneToZeroCompareMap(dataList);
            for (int i = 0; i < dataList.size(); i++) {
                if (removedIndices.contains(i))
                    continue;

                if (onlyOneRemains(dataList, removedIndices))
                    break;

                if (filteredOut(dataList.get(i), flip, currentIndex, oneToZeroCompare)) {
                    removedIndices.add(i);
                }
            }

            if (onlyOneRemains(dataList, removedIndices))
                break;

            dataList = getKept(dataList, removedIndices);
            removedIndices.clear();
            log.debug("idx = {}, kept: {}", currentIndex, dataList);

            currentIndex += 1;

        }
        log.debug("idx = {}, kept: {}", currentIndex, dataList);

        for (int i = 0; i < dataList.size(); i++) {
            if (!removedIndices.contains(i)) {
                return dataList.get(i);
            }
        }

        return null;
    }

    private Map<Integer, Integer> oneToZeroCompareMap(List<String> dataList) {
        return getOneBitCounts(dataList).entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        it -> compareOneToZero(it.getValue(), dataList.size())));
    }

    private List<String> getKept(List<String> dataList, Set<Integer> removedIndices) {

        List<String> result = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            if (!removedIndices.contains(i))
                result.add(dataList.get(i));
        }
        return result;
    }

    private boolean onlyOneRemains(List<String> dataList, Set<Integer> removedIndices) {
        return removedIndices.size() + 1 >= dataList.size();
    }

    private boolean filteredOut(String s, boolean flip, int currentIndex, Map<Integer, Integer> oneMostCommon) {

        String substring = s.substring(currentIndex, currentIndex + 1);
        if (flip ^ oneMostCommon.get(currentIndex) >= 0) {
            return substring.startsWith("0");
        } else {
            return substring.startsWith("1");
        }
    }


    @Override
    public int getDayNumber() {
        return 3;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
