package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

        String[] input = data.split("\n\n");

        List<Rule> ruleList = Arrays.stream(input[0].split("\n")).map(Rule::fromString).collect(Collectors.toList());

        Ticket myTicket = Ticket.fromString(input[1].split("\n")[1]);

        List<Ticket> otherTickets = Arrays.stream(input[2].split("\n")).skip(1).map(Ticket::fromString).collect(Collectors.toList());
        otherTickets.add(myTicket);

        List<Ticket> validTickets = new ArrayList<>();
        for (Ticket ticket : otherTickets) {
            boolean valid = true;
            for (Integer value : ticket.getValues()) {

                if (!validForAnyRule(value, ruleList)) {
                    valid = false;
                    break;
                }
            }

            if (valid)
                validTickets.add(ticket);
        }

        final List<Set<String>> possibleNameForNthNumber = myTicket.getValues().stream().map(it -> initPossibleClasses(ruleList)).collect(Collectors.toList());


        for (Ticket ticket : validTickets) {

            final List<Integer> ticketValues = ticket.getValues();
            for (int i = 0; i < ticketValues.size(); i++) {
                final Integer value = ticketValues.get(i);

                for (Rule rule : ruleList) {

                    if (!rule.validate(value)) {

                        possibleNameForNthNumber.get(i).remove(rule.getName());

                    }

                }
            }
        }

        Set<String> solvedSingletons = new HashSet<>();
        while (solvedSingletons.size() < possibleNameForNthNumber.stream().filter(it -> it.size() == 1).count()) {

            for (int i = 0; i < possibleNameForNthNumber.size(); i++) {

                final Set<String> stringSet = possibleNameForNthNumber.get(i);

                final String mySingleton = stringSet.stream().findAny().orElse("");
                if (stringSet.size() == 1 && !solvedSingletons.contains(mySingleton)) {

                    for (int j = 0; j < possibleNameForNthNumber.size(); j++) {
                        if (i == j)
                            continue;

                        possibleNameForNthNumber.get(j).remove(mySingleton);
                        solvedSingletons.add(mySingleton);
                    }

                }
            }

        }

        List<Set<String>> chosenVariant = chooseCorrectVariant(possibleNameForNthNumber);

        List<Integer> idxWithDepartureList = new ArrayList<>();
        for (int i = 0; i < chosenVariant.size(); i++) {

            if (chosenVariant.get(i).stream().anyMatch(s -> s.startsWith("departure")))
                idxWithDepartureList.add(i);
        }

        if (idxWithDepartureList.size() == 6) {

            long result = 1;
            for (Integer idx : idxWithDepartureList) {
                result *= myTicket.getValues().get(idx);
            }
            return result;


        }
        return 0L;
    }


    // recursion not needed somehow lol
    private static List<Set<String>> chooseCorrectVariant(List<Set<String>> setList) {

        if (setList.stream().anyMatch(Set::isEmpty))
            return null;

        if (setList.stream().allMatch(it -> it.size() == 1))
            return setList;

        for (int i = 0; i < setList.size(); i++) {

            final Set<String> stringSet = setList.get(i);

            if (stringSet.size() <= 1)
                continue;

            final List<String> variants = new ArrayList<>(stringSet);

            for (String variant : variants) {

                List<Set<String>> workCopy = getCopy(setList);
                workCopy.get(i).clear();

                final String chosen = variant;
                for (Set<String> workCopySet : workCopy) {
                    workCopySet.remove(chosen);
                }
                workCopy.get(i).add(chosen);

                List<Set<String>> sub = chooseCorrectVariant(workCopy);

                if (sub != null)
                    return sub;

            }
        }

        return null;
    }

    private static List<Set<String>> getCopy(List<Set<String>> setList) {

        return setList.stream().map(HashSet::new).collect(Collectors.toList());

    }












    private static Set<String> initPossibleClasses(List<Rule> ruleList) {
        return ruleList.stream().map(it -> it.getName()).collect(Collectors.toSet());
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
