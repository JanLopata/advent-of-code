package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Day06 extends Day {

    public static void main(String[] args) throws IOException {
        new Day06().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] groups = data.split("\n\n");

        return Arrays.stream(groups).mapToLong(this::countPositiveAnswers).sum();

    }

    @Override
    public Object part2(String data) {

        String[] groups = data.split("\n\n");

        return Arrays.stream(groups).mapToLong(this::countEveryonePositiveAnswers).sum();

    }

    private long countPositiveAnswers(String group) {
        String[] lines = group.split("\n");

        Set<Integer> groupPositives = new HashSet<>();
        for (String line : lines) {
            Set<Integer> positivesSet = line.chars().boxed().collect(Collectors.toSet());
            groupPositives.addAll(positivesSet);
        }

        return groupPositives.size();
    }

    private long countEveryonePositiveAnswers(String group) {

        String[] lines = group.split("\n");
        Boolean[] answerCheck = Collections.nCopies(26, true).toArray(new Boolean[0]);

        for (String line : lines) {

            Set<Integer> positivesSet = line.chars().boxed().collect(Collectors.toSet());

            for (int i = 0; i < answerCheck.length; i++) {
                answerCheck[i] = answerCheck[i] && positivesSet.contains((int) 'a' + i);
            }

        }

        return Arrays.stream(answerCheck).filter(it -> it).count();
    }

    @Override
    public int getDayNumber() {
        return 6;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
