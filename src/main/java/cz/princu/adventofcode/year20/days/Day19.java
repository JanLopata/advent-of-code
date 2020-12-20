package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class Day19 extends Day {
    public static void main(String[] args) throws IOException {
        new Day19().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        String rulesPart = input[0];
        String messagesPart = input[1];

        final Map<Long, Rule> rulesMap = parseRulesMap(rulesPart);

        long result = 0;

        for (String message : messagesPart.split("\n")) {

            final Rule firstRule = rulesMap.get(0L);

            for (List<Long> subRule : firstRule.getSubRules()) {
                if (test(message, rulesMap, subRule)) {
                    result++;
                    break;
                }
            }
        }


        return result;
    }

    private boolean test(String message,
                         Map<Long, Rule> rulesMap,
                         List<Long> currentRulesChecking) {


        log.debug("Testing {} for {}", message, currentRulesChecking);

        if (currentRulesChecking.isEmpty() && message.isEmpty())
            return true;

        if (currentRulesChecking.isEmpty()) {
            return false;
        }

        if (message.isEmpty()) {
            return false;
        }

        final List<Long> shortRules = currentRulesChecking.subList(1, currentRulesChecking.size());
        final String shortMessage = message.substring(1);
        final Rule rule = rulesMap.get(currentRulesChecking.get(0));
        if (rule.getCharacter() != null) {
            boolean oneCharCheckResult = rule.getCharacter().equals(message.charAt(0));
            if (!oneCharCheckResult) {
                return false;
            }

            return test(shortMessage, rulesMap, shortRules);
        } else {

            for (List<Long> subRules : rule.subRules) {

                List<Long> newRules = new ArrayList<>(subRules);
                newRules.addAll(shortRules);

                if (test(message, rulesMap, newRules))
                    return true;

            }
            return false;

        }
    }

    private Map<Long, Rule> parseRulesMap(String rulesPart) {
        final String[] rulesRows = rulesPart.split("\n");
        Map<Long, Rule> ruleMap = new HashMap<>();

        for (String ruleString : rulesRows) {
            final int ruleNumberToIdx = ruleString.indexOf(":");
            long number = Long.parseLong(ruleString.substring(0, ruleNumberToIdx));

            String inside = ruleString.substring(ruleNumberToIdx + 2);

            Rule rule;
            if (inside.indexOf('"') > -1) {
                rule = new Rule(number, Collections.emptyList(), inside.charAt(inside.indexOf('"') + 1));

            } else {

                rule = new Rule(number, new ArrayList<>(), null);
                final String[] rulesSplit = inside.split(" \\| ");
                for (String s : rulesSplit) {

                    List<Long> sub =
                            Arrays.stream(s.split(" "))
                                    .map(Long::parseLong)
                                    .collect(Collectors.toList());

                    rule.getSubRules().add(sub);

                }
            }
            ruleMap.put(number, rule);
        }
        return ruleMap;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    private static class Rule {

        private Long number;
        private List<List<Long>> subRules;
        private Character character;

    }


    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 19;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
