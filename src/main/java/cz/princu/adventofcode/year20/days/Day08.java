package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day08 extends Day {

    public static void main(String[] args) throws IOException {
        new Day08().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");


        return processCodeAndGetAccumulatorBeforeLoop(lines);
    }

    private Long processCodeAndGetAccumulatorBeforeLoop(String[] lines) {

        int currentLineNumber = 0;
        long accumulator = 0;
        Set<Integer> visited = new HashSet<>();

        while (!visited.contains(currentLineNumber)) {

            visited.add(currentLineNumber);

            String currentLine = lines[currentLineNumber];


            String instruction = currentLine.substring(0, 3);
            int argument = getLineArgument(currentLine);

            if ("nop".equals(instruction)) {
                currentLineNumber += 1;

            }

            if ("acc".equals(instruction)) {
                currentLineNumber += 1;
                accumulator += argument;
            }

            if ("jmp".equals(instruction)) {
                currentLineNumber += argument;
            }

        }

        return accumulator;

    }

    private Long processHackedCodeAndGetAccumulatorAtEnd(String[] lines, int hackedInstructionIdx) {

        int currentLineNumber = 0;
        long accumulator = 0;
        Set<Integer> visited = new HashSet<>();

        while (currentLineNumber != lines.length) {

            if (visited.contains(currentLineNumber))
                throw new IllegalStateException();

            if (currentLineNumber < 0 || currentLineNumber > lines.length)
                throw new IllegalStateException();

            visited.add(currentLineNumber);

            String currentLine = lines[currentLineNumber];


            String instruction = currentLine.substring(0, 3);
            int argument = getLineArgument(currentLine);


            if (currentLineNumber == hackedInstructionIdx)
                instruction = hackInstruction(instruction);


            if ("nop".equals(instruction)) {
                currentLineNumber += 1;
            }

            if ("acc".equals(instruction)) {
                currentLineNumber += 1;
                accumulator += argument;
            }

            if ("jmp".equals(instruction)) {
                currentLineNumber += argument;
            }

        }

        return accumulator;

    }

    private String hackInstruction(String instruction) {
        if ("jmp".equals(instruction))
            return "nop";

        if ("nop".equals(instruction))
            return "jmp";

        throw new IllegalStateException();
    }


    private int getLineArgument(String currentLine) {
        String argumentString = currentLine.substring(4);

        if (argumentString.isEmpty())
            return 0;

        return Integer.parseInt(argumentString);
    }


    @Override
    public Object part2(String data) {
        String[] lines = data.split("\n");


        for (int i = 0; i < lines.length; i++) {
            try {
                return processHackedCodeAndGetAccumulatorAtEnd(lines, i);
            } catch (IllegalStateException e) {
                // ignore
            }
        }

        return 0L;

    }

    @Override
    public int getDayNumber() {
        return 8;
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
