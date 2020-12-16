package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day16 extends Day {
    public static void main(String[] args) throws IOException {
        new Day16().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        List<Rule> ruleList = Arrays.stream(input[0].split("\n")).map(Rule::fromString).collect(Collectors.toList());

//        Ticket myTicket = Ticket.fromString(input[1].split("\n")[1]);

        List<Ticket> otherTickets = Arrays.stream(input[2].split("\n")).skip(1).map(Ticket::fromString).collect(Collectors.toList());

        long result = 0L;
        for (Ticket ticket : otherTickets) {


            for (Integer value : ticket.getValues()) {

                if (!validForAnyRule(value, ruleList))
                    result += value;

            }

        }


        return result;
    }

    private boolean validForAnyRule(int value, Collection<Rule> rules) {

        return rules.stream().anyMatch(rule -> rule.validate(value));

    }


    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @RequiredArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    @ToString
    private static class Rule {

        private final String origin;
        private final String name;
        private final int low1;
        private final int high1;
        private final int low2;
        private final int high2;

        public static Rule fromString(String string) {
            final String[] split1 = string.split(": ");
            final String[] split2 = split1[1].split(" or ");
            final String[] splitLowHigh1 = split2[0].split("-");
            final String[] splitLowHigh2 = split2[1].split("-");
            return new Rule(string,
                    split1[0],
                    Integer.parseInt(splitLowHigh1[0]),
                    Integer.parseInt(splitLowHigh1[1]),
                    Integer.parseInt(splitLowHigh2[0]),
                    Integer.parseInt(splitLowHigh2[1]));
        }

        public boolean validate(int value) {
            return ((value >= low1 && value <= high1) || (value >= low2 && value <= high2));
        }

    }


    @RequiredArgsConstructor
    @ToString
    private static class Ticket {

        @Getter
        private final List<Integer> values;

        public static Ticket fromString(String s) {
            return new Ticket(
                    Arrays.stream(s.split(",")).map(Integer::parseInt).collect(Collectors.toList()));
        }
    }

    @Override
    public int getDayNumber() {
        return 16;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
