package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Day02 extends Day {
    public static void main(String[] args) throws IOException {
        new Day02().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        long depth = 0;
        long horiz = 0;

        for (String line : input) {

            String[] command = line.split(" ");
            int value = Integer.parseInt(command[1]);

            if (command[0].startsWith("fo"))
                horiz += value;

            if (command[0].startsWith("do"))
                depth += value;

            if (command[0].startsWith("up"))
                depth -= value;

        }


        return depth * horiz;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        long aim = 0;
        long depth = 0;
        long horiz = 0;

        for (String line : input) {

            String[] command = line.split(" ");
            int value = Integer.parseInt(command[1]);

            if (command[0].startsWith("fo")) {
                horiz += value;
                depth += aim * value;
            }

            if (command[0].startsWith("do"))
                aim += value;

            if (command[0].startsWith("up"))
                aim -= value;

        }

        return depth * horiz;
    }


    @Override
    public int getDayNumber() {
        return 2;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
