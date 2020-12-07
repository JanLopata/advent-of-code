package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
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

        Map<String, BagRule> bagRuleMap = Arrays.stream(lines)
                .map(this::parseRule)
                .collect(Collectors.toMap(BagRule::getOuterBag, it -> it));

        return countBagsThatCanContain("shiny gold", bagRuleMap);
    }

    private Long countBagsThatCanContain(String bagToContain, Map<String, BagRule> bagRuleMap) {

        return bagRuleMap.keySet().stream()
                .filter(outerBag -> canEventuallyContain(outerBag, bagToContain, bagRuleMap))
                .count();

    }

    private boolean canEventuallyContain(String outerBag, String containingBagName, Map<String, BagRule> bagRuleMap) {

        if (!bagRuleMap.containsKey(outerBag))
            return false;

        BagRule outerBagRule = bagRuleMap.get(outerBag);
        if (outerBagRule.getInnerBagsMap().containsKey(containingBagName))
            return true;

        for (String innerBag : outerBagRule.getInnerBagsMap().keySet()) {

            if (canEventuallyContain(innerBag, containingBagName, bagRuleMap))
                return true;

        }

        return false;
    }

    private BagRule parseRule(String s) {

        String[] ruleParts = s.replace(" bags", "")
                .replace(" bag", "").replace(".", "")
                .split(" contain ");

        String outerBag = ruleParts[0];
        String innerBagsToParse = ruleParts[1];

        return BagRule.builder()
                .outerBag(outerBag)
                .innerBagsMap(parseInnerBags(innerBagsToParse))
                .build();
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

        String[] lines = data.split("\n");

        Map<String, BagRule> bagRuleMap = Arrays.stream(lines)
                .map(this::parseRule)
                .collect(Collectors.toMap(BagRule::getOuterBag, it -> it));

        return countBagsContaining("shiny gold", bagRuleMap) - 1L;

    }

    private Long countBagsContaining(String head, Map<String, BagRule> bagRuleMap) {

        if (!bagRuleMap.containsKey(head))
            return 0L;

        BagRule headBagRule = bagRuleMap.get(head);

        long sum = 1L;
        for (Map.Entry<String, Integer> bagNameAndCount : headBagRule.innerBagsMap.entrySet()) {

            sum += bagNameAndCount.getValue() * countBagsContaining(bagNameAndCount.getKey(), bagRuleMap);

        }
        return sum;
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
