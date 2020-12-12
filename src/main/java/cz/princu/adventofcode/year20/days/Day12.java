package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day12 extends Day {

    public static void main(String[] args) throws IOException {
        new Day12().printParts();
    }

    private static final Set<Character> TURNING_CHAR;
    private static final Set<Character> COMPASS_CHAR;
    private static final List<Character> LEFT_TURNING;
    private static final List<Character> RIGHT_TURNING;

    static {

        TURNING_CHAR = Stream.of('L', 'R').collect(Collectors.toSet());
        COMPASS_CHAR = Stream.of('N', 'E', 'W', 'S').collect(Collectors.toSet());

        LEFT_TURNING = Stream.of('N', 'W', 'S', 'E', 'N', 'W', 'S', 'E').collect(Collectors.toList());
        RIGHT_TURNING = Stream.of('N', 'E', 'S', 'W', 'N', 'E', 'S', 'W').collect(Collectors.toList());

    }


    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");

        ShipStatus shipStatus = initShip();

        for (String line : lines) {
            carryFerry(shipStatus, line);
        }


        return shipStatus.getPosition().getManhattanDistance();

    }

    private ShipStatus initShip() {
        Coords position = new Coords(0, 0);
        char facing = 'E';
        Coords waypointRelative = new Coords(10, 1);
        return new ShipStatus(position, facing, waypointRelative);
    }

    private void carryFerry(ShipStatus shipStatus, String line) {

        final char instruction = line.charAt(0);
        final int step = Integer.parseInt(line.substring(1));

        if (instruction == 'F')
            shipStatus.setPosition(applyFacing(shipStatus.getFacing(), step, shipStatus.getPosition()));

        if (TURNING_CHAR.contains(instruction))
            shipStatus.setFacing(changeFacing(shipStatus.getFacing(), instruction, step));

        if (COMPASS_CHAR.contains(instruction))
            shipStatus.setPosition(applyFacing(instruction, step, shipStatus.getPosition()));

    }

    private char changeFacing(char facing, char instruction, int step) {

        List<Character> turningHelper;

        if (instruction == 'L')
            turningHelper = LEFT_TURNING;
        else
            turningHelper = RIGHT_TURNING;

        int i = 0;
        while (facing != turningHelper.get(i)) i++;

        return turningHelper.get(i + step / 90);
    }

    private Coords applyFacing(char facing, int step, Coords coords) {

        switch (facing) {

            case 'N':
                return new Coords(coords.getX(), coords.getY() + step);

            case 'S':
                return new Coords(coords.getX(), coords.getY() - step);

            case 'E':
                return new Coords(coords.getX() + step, coords.getY());

            case 'W':
                return new Coords(coords.getX() - step, coords.getY());

            default:
                throw new IllegalStateException("Unexpected value: " + facing);
        }

    }

    private Coords applyWaypoint(Coords position, Coords waypoint, int step) {

        return new Coords(
                position.getX() + waypoint.getX() * step,
                position.getY() + waypoint.getY() * step
        );
    }

    private Coords rotateWaypoint(Coords waypointCoords, char instruction, int step) {

        Coords result = new Coords(waypointCoords.getX(), waypointCoords.getY());

        for (int i = 0; i < step / 90; i++) {

            if (instruction == 'L') {
                result = new Coords(-result.getY(), result.getX());
            } else {
                result = new Coords(result.getY(), -result.getX());
            }

        }

        return result;
    }


    private void navigateFerry(ShipStatus shipStatus, String line) {

        final char instruction = line.charAt(0);
        final int step = Integer.parseInt(line.substring(1));

        if (instruction == 'F')
            shipStatus.setPosition(applyWaypoint(shipStatus.getPosition(), shipStatus.getWaypointRelative(), step));

        if (TURNING_CHAR.contains(instruction))
            shipStatus.setWaypointRelative(rotateWaypoint(shipStatus.getWaypointRelative(), instruction, step));

        if (COMPASS_CHAR.contains(instruction))
            shipStatus.setWaypointRelative(applyFacing(instruction, step, shipStatus.getWaypointRelative()));

    }


    @Override
    public Object part2(String data) {

        String[] lines = data.split("\n");

        ShipStatus shipStatus = initShip();

        for (String line : lines) {
            navigateFerry(shipStatus, line);
        }

        return shipStatus.getPosition().getManhattanDistance();
    }


    @Override
    public int getDayNumber() {
        return 12;
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @ToString
    private static class ShipStatus {

        private Coords position;
        private char facing;

        private Coords waypointRelative;

    }


    @AllArgsConstructor
    @Getter
    @ToString
    private static class Coords {

        final int x;
        final int y;

        public int getManhattanDistance() {
            return (x > 0 ? x : -x) + (y > 0 ? y : -y);
        }

    }


}
