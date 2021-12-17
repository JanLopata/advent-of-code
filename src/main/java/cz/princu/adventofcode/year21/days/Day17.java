package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class Day17 extends Day {
    public static void main(String[] args) throws IOException {
        new Day17().printParts();
    }

    private static final Pattern pattern = Pattern.compile(
            "target area: x=(-?[0-9]+)..(-?[0-9]+), y=(-?[0-9]+)..(-?[0-9]+)");

    @Override
    public Object part1(String data) {

        var matcher = pattern.matcher(data);
        if (!matcher.matches()) {
            return -1;
        }

        var targetY1 = Integer.parseInt(matcher.group(3));
        var targetY2 = Integer.parseInt(matcher.group(4));

        var top = Math.max(targetY1, targetY2);
        var bottom = Math.min(targetY1, targetY2);

        var yVelocity = findYTopLimit(top, bottom);
        return modelYTrajectory(yVelocity, bottom).stream().mapToLong(it -> it).max().orElse(-1);

    }

    private int findYTopLimit(int top, int bottom) {
        for (int i = 1000; i > -100; i--) {
            var yTrajectory = modelYTrajectory(i, bottom);
            var acceptable = isAcceptable(yTrajectory, top, bottom);

            if (acceptable) {
                return i;
            }
        }
        return -1;
    }

    private int findYBottomLimit(int top, int bottom) {
        for (int i = -200; i < 0; i++) {
            var yTrajectory = modelYTrajectory(i, bottom);
            var acceptable = isAcceptable(yTrajectory, top, bottom);

            if (acceptable) {
                return i;
            }
        }
        return 1;
    }

    private int findXTopLimit(int left, int right) {

        for (int i = 1000; i > 0; i--) {
            var xTrajectory = modelXTrajectory(i, right);
            var acceptable = isAcceptable(xTrajectory, right, left);
            if (acceptable) {
                return i;
            }
        }
        return -1;

    }

    private int findXBottomLimit(int left, int right) {

        for (int i = 0; i < 1000; i++) {

            var xTrajectory = modelXTrajectory(i, right);
            var acceptable = isAcceptable(xTrajectory, right, left);
            if (acceptable) {
                return i;
            }
        }
        return -1;

    }

    private boolean isAcceptable(List<Integer> trajectory, int upper, int lower) {
        var last = getLast(trajectory);
        return last >= lower && last <= upper;
    }

    private List<Integer> modelYTrajectory(int velocity, int bottom) {

        int y = 0;
        List<Integer> result = new ArrayList<>();

        while (y >= bottom) {

            result.add(y);
            y += velocity;
            velocity -= 1;

        }

        return result;

    }

    private List<Integer> modelXTrajectory(int velocity, int right) {

        return modelXTrajectory(velocity, right, 0);
    }

    private List<Integer> modelXTrajectory(int velocity, int right, int expectedSize) {

        int x = 0;
        List<Integer> result = new ArrayList<>();

        while (x <= right) {

            result.add(x);
            x += velocity;
            velocity -= velocity > 0 ? 1 : 0;
            if (velocity == 0 && result.size() >= expectedSize)
                break;
        }

        return result;
    }


    @Override
    public Object part2(String data) {

        var matcher = pattern.matcher(data);
        if (!matcher.matches()) {
            return -1;
        }

        var targetY1 = Integer.parseInt(matcher.group(3));
        var targetY2 = Integer.parseInt(matcher.group(4));

        var top = Math.max(targetY1, targetY2);
        var bottom = Math.min(targetY1, targetY2);

        var left = Integer.parseInt(matcher.group(1));
        var right = Integer.parseInt(matcher.group(2));

        var yTopLimit = findYTopLimit(top, bottom);
        var yBottomLimit = findYBottomLimit(top, bottom);

        var xTopLimit = findXTopLimit(left, right);
        var xBottomLimit = findXBottomLimit(left, right);

        long acceptableCount = 0;
        for (int x = xBottomLimit; x <= xTopLimit; x++) {
            for (int y = yBottomLimit; y <= yTopLimit; y++) {

                var yTrajectory = modelYTrajectory(y, bottom);
                var xTrajectory = modelXTrajectory(x, right, yTrajectory.size());

                if (xTrajectory.size() < yTrajectory.size()) {
                    yTrajectory = yTrajectory.stream().limit(xTrajectory.size()).collect(Collectors.toList());
                }

                if (xTrajectory.size() > yTrajectory.size()) {
                    xTrajectory = xTrajectory.stream().limit(yTrajectory.size()).collect(Collectors.toList());
                }

                if (!(isAcceptable(xTrajectory, right, left) && isAcceptable(yTrajectory, top, bottom)))
                    continue;

                acceptableCount += 1;
            }
        }

        return acceptableCount;
    }


    private Integer getLast(List<Integer> xTrajectory) {
        return xTrajectory.get(xTrajectory.size() - 1);
    }


    @Override
    public int getDayNumber() {
        return 17;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
