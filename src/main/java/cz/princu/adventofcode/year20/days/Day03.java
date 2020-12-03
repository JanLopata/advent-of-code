package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;

public class Day03 extends Day {

    public static void main(String[] args) throws IOException {
        new Day03().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");

        return getTreeEncounteredOnTrajectory(lines, 1, 3);
    }

    private int getTreeEncounteredOnTrajectory(String[] lines, int slopeDown, int slopeRight) {
        int column = 0;
        int treeCount = 0;
        for (int i = 0; i < lines.length; i = i + slopeDown) {

            String line = lines[i];
            if (line.charAt(column) == '#')
                treeCount++;

            column = (column + slopeRight) % line.length();
        }
        return treeCount;
    }

    @Override
    public Object part2(String data) {

        String[] lines = data.split("\n");
        return (long) getTreeEncounteredOnTrajectory(lines, 1, 1)
                * getTreeEncounteredOnTrajectory(lines, 1, 3)
                * getTreeEncounteredOnTrajectory(lines, 1, 5)
                * getTreeEncounteredOnTrajectory(lines, 1, 7)
                * getTreeEncounteredOnTrajectory(lines, 2, 1);

    }

    @Override
    public int getDayNumber() {
        return 3;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
