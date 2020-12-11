package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class Day11 extends Day {

    public static void main(String[] args) throws IOException {
        new Day11().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");


        char[][] dataField = new char[lines.length][];

        for (int i = 0; i < lines.length; i++) {
            dataField[i] = lines[i].toCharArray();
        }

        Set<Integer> knownFields = new HashSet<>();
        knownFields.add(Arrays.deepHashCode(dataField));

        char[][] workField = generateNewField(dataField);

        while (!knownFields.contains(Arrays.deepHashCode(workField))) {

            knownFields.add(Arrays.deepHashCode(workField));

            printField(workField);
            log.info("hash: {}", Arrays.deepHashCode(workField));
            workField = generateNewField(workField);
        }

        printField(workField);
        log.info("hash: {}", Arrays.deepHashCode(workField));

        return countOccupied(workField);


    }

    private Long countOccupied(char[][] dataField) {

        long result = 0;
        for (char[] chars : dataField) {

            for (char c : chars) {

                if (c == '#')
                    result++;

            }
        }

        return result;

    }

    private char[][] generateNewField(char[][] dataField) {

        char[][] result = new char[dataField.length][];
        for (int i = 0; i < dataField.length; i++) {
            result[i] = new char[dataField[i].length];

            for (int j = 0; j < dataField[i].length; j++) {

                char target = dataField[i][j];

                final char current = dataField[i][j];

                if (current == '#' && countOccupiedAround(dataField, i, j) >= 4)
                    target = 'L';


                if (current == 'L' && countOccupiedAround(dataField, i, j) == 0)
                    target = '#';

                result[i][j] = target;

            }

        }

        return result;

    }

    private int countOccupiedAround(char[][] field, int idx1, int idx2) {

        int result = 0;
        for (int i = idx1 - 1; i <= idx1 + 1; i++) {
            if (i < 0 || i >= field.length)
                continue;

            for (int j = idx2 - 1; j <= idx2 + 1; j++) {

                if (j < 0 || j >= field[i].length)
                    continue;

                if (i == idx1 && j == idx2)
                    continue;

                if (field[i][j] == '#')
                    result++;
            }

        }

        return result;
    }

    private void printField(char[][] field) {

        StringBuilder sb = new StringBuilder();

        for (char[] chars : field) {

            for (char chachar : chars) {
                sb.append(chachar);
            }

            sb.append("\n");

        }

        log.info("\n{}", sb.toString());

    }


    @Override
    public Object part2(String data) {

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 11;
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
