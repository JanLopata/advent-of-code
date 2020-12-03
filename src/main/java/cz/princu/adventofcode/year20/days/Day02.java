package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 extends Day {

    public static void main(String[] args) throws IOException {
        new Day02().printParts();
    }


    private static final Pattern INPUT_ROW_PATTERN = Pattern.compile("(\\d+)-(\\d+) ([a-z]): (.*)");


    @Override
    public Object part1(String data) {

        return Arrays.stream(data.split("\n"))
                .filter(this::isValidPasswordByFrequency)
                .count();

    }

    private boolean isValidPasswordByFrequency(String s) {
        Matcher matcher = INPUT_ROW_PATTERN.matcher(s);

        if (!matcher.matches())
            return false;

        int minOccurrence = Integer.parseInt(matcher.group(1));
        int maxOccurrence = Integer.parseInt(matcher.group(2));

        char policyChar = matcher.group(3).charAt(0);

        long policyCharCount = matcher.group(4).chars().filter(ch -> ch == policyChar).count();

        return policyCharCount >= minOccurrence && policyCharCount <= maxOccurrence;
    }

    @Override
    public Object part2(String data) {

        return Arrays.stream(data.split("\n"))
                .filter(this::isValidPasswordByCharCheck)
                .count();

    }

    private boolean isValidPasswordByCharCheck(String s) {
        Matcher matcher = INPUT_ROW_PATTERN.matcher(s);

        if (!matcher.matches())
            return false;

        char policyChar = matcher.group(3).charAt(0);
        String passToCheck = matcher.group(4);
        return (policyChar == passToCheck.charAt(Integer.parseInt(matcher.group(1)) - 1))
                != (policyChar == passToCheck.charAt(Integer.parseInt(matcher.group(2)) - 1));

    }

    @Override
    public int getDayNumber() {
        return 2;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
