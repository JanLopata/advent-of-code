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


@Slf4j
public class Day08 extends Day {
    public static void main(String[] args) throws IOException {
        new Day08().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        Set<Integer> oneFourSevenEightLengths = Set.of(2, 3, 4, 7);
        long result = 0;
        for (String row : input) {

            var leftRight = row.split("\\|");

            var values = leftRight[1].split(" ");

            var currentCount = Arrays.stream(values).filter(it -> oneFourSevenEightLengths.contains(it.length())).count();
            result += currentCount;

        }

        return result;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        long result = 0;
        for (String row : input) {

            var leftRight = row.split(" \\| ");

            var setToNumberMap = createSets(leftRight[0]);
            solve(setToNumberMap);

            var signal = readSignal(leftRight[1]);

            int digitValue = 1000;
            for (Set<Character> characters : signal) {
                var digit = setToNumberMap.get(characters);
                result += digit * digitValue;
                digitValue /= 10;
            }

        }

        return result;
    }

    private void solve(Map<Set<Character>, Integer> setToNumberMap) {

        var set1 = setToNumberMap.keySet().stream().filter(it -> it.size() == 2).findFirst()
                .orElseThrow(() -> new IllegalStateException("1 not found"));
        var set4 = setToNumberMap.keySet().stream().filter(it -> it.size() == 4).findFirst()
                .orElseThrow(() -> new IllegalStateException("4 not found"));
        var set7 = setToNumberMap.keySet().stream().filter(it -> it.size() == 3).findFirst()
                .orElseThrow(() -> new IllegalStateException("7 not found"));
        var set8 = setToNumberMap.keySet().stream().filter(it -> it.size() == 7).findFirst()
                .orElseThrow(() -> new IllegalStateException("8 not found"));

        Set<Set<Character>> solved = new HashSet<>();
        solved.add(set1);
        solved.add(set4);
        solved.add(set7);
        solved.add(set8);

        var set3 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 5)
                .filter(it -> firstContainsSecond(it, set1))
                .filter(it -> !firstContainsSecond(it, set4))
                .filter(it -> firstContainsSecond(it, set7))
                .findAny().orElseThrow(() -> new IllegalStateException("3 not found"));
        solved.add(set3);

        var set9 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 6)
                .filter(it -> firstContainsSecond(it, set1))
                .filter(it -> firstContainsSecond(it, set4))
                .filter(it -> firstContainsSecond(it, set7))
                .findAny().orElseThrow(() -> new IllegalStateException("9 not found"));
        solved.add(set9);

        var set0 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 6)
                .filter(it -> firstContainsSecond(it, set1))
                .filter(it -> !firstContainsSecond(it, set4))
                .filter(it -> firstContainsSecond(it, set7))
                .findAny().orElseThrow(() -> new IllegalStateException("0 not found"));
        solved.add(set0);

        var set2 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 5)
                .filter(it -> !firstContainsSecond(it, set1))
                .filter(it -> !firstContainsSecond(it, set4))
                .filter(it -> !firstContainsSecond(set9, it))
                .findAny().orElseThrow(() -> new IllegalStateException("2 not found"));
        solved.add(set2);

        var set5 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 5)
                .findAny().orElseThrow(() -> new IllegalStateException("5 not found"));
        solved.add(set5);

        var set6 = setToNumberMap.keySet().stream().filter(it -> !solved.contains(it))
                .filter(it -> it.size() == 6)
                .findAny().orElseThrow(() -> new IllegalStateException("6 not found"));
        solved.add(set6);

        setToNumberMap.put(set0, 0);
        setToNumberMap.put(set1, 1);
        setToNumberMap.put(set2, 2);
        setToNumberMap.put(set3, 3);
        setToNumberMap.put(set4, 4);
        setToNumberMap.put(set5, 5);
        setToNumberMap.put(set6, 6);
        setToNumberMap.put(set7, 7);
        setToNumberMap.put(set8, 8);
        setToNumberMap.put(set9, 9);

    }

    private boolean firstContainsSecond(Set<Character> it, Set<Character> set1) {
        return it.containsAll(set1);
    }


    private Map<Set<Character>, Integer> createSets(String s) {
        Map<Set<Character>, Integer> setToNumberMap = new HashMap<>();
        for (String s1 : s.split(" ")) {

            Set<Character> charSet = new HashSet<>();
            for (String s2 : s1.split("")) {
                charSet.add(s2.charAt(0));
            }

            setToNumberMap.put(charSet, -1);
        }
        return setToNumberMap;

    }

    private List<Set<Character>> readSignal(String s) {
        var result = new ArrayList<Set<Character>>();
        for (String s1 : s.split(" ")) {

            Set<Character> charSet = new HashSet<>();
            for (String s2 : s1.split("")) {
                try {
                    charSet.add(s2.charAt(0));
                } catch (Exception e) {
                    log.debug("Day08::readSignal: ", e);
                }
            }

            result.add(charSet);
        }

        return result;
    }


    @Override
    public int getDayNumber() {
        return 8;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
