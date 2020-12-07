package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day07 extends Day {

    public static void main(String[] args) throws IOException {
        new Day07().printParts();
    }


    private static final Pattern BAGS_WITH_COUNT_REGEX = Pattern.compile("(\\d+) ([a-z]+ [a-z]+)");

    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");

        List<BagRule> rules = Arrays.stream(lines).map(this::parseRule).collect(Collectors.toList());


        return 0L;
    }

    private BagRule parseRule(String s) {

        String[] ruleParts = s.replace(" bags", "")
                .replace(" bag", "").replace(".", "")
                .split(" contain ");

        String outerBag = ruleParts[0];
        String innerBagsToParse = ruleParts[1];

        BagRule rule = BagRule.builder()
                .outerBag(outerBag)
                .innerBagsMap(parseInnerBags(innerBagsToParse))
                .build();

        return rule;
    }

    private Map<String, Integer> parseInnerBags(String innerBagsToParse) {

        if (innerBagsToParse.equals("no other"))
            return Collections.emptyMap();

        String[] bagsCounts = innerBagsToParse.split(", ");
        HashMap<String, Integer> result = new HashMap<>();

        for (String bagsCountString : bagsCounts) {
            Matcher matcher = BAGS_WITH_COUNT_REGEX.matcher(bagsCountString);
            if (matcher.matches()) {
                result.put(matcher.group(2), Integer.valueOf(matcher.group(1)));
            }
        }

        return result;

    }

    @Override
    public Object part2(String data) {


        return 0L;

    }

    @Override
    public int getDayNumber() {
        return 7;
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
